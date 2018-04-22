package com.zsm.commonexample.event;

import org.apache.log4j.Logger;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.GenericTypeResolver;

import java.util.*;
import java.util.concurrent.ExecutorService;

@SuppressWarnings("unchecked")
public class EventProducer
{
    private static final Logger logger = Logger.getLogger(EventProducer.class);

    private ExecutorService executorService;

    /**
     * 事件监听器存储容器
     */
    private Set<ListenerEvent> eventListenerSet = new HashSet<ListenerEvent>();

    private Map<Class<? extends triggerEvent>, ListenerRegistry> cachedEventListeners = new HashMap<>();

    /**
     * 注册监听器
     *
     * @param listenerEvent
     */
    public void addListener(ListenerEvent listenerEvent)
    {
        eventListenerSet.add(listenerEvent);
    }

    /**
     * 删除监听器
     *
     * @param listenerEvent
     */
    public void removeListener(ListenerEvent listenerEvent)
    {
        eventListenerSet.remove(listenerEvent);
    }

    /**
     * 清除所有注册的监听器
     */
    public void removeAllListeners()
    {
        eventListenerSet.clear();
    }

    /**
     * 组播事件给相应的注册监听器 - 异步执行
     *
     * @param event
     */
    public void multicastEvent(final triggerEvent event)
    {
        try
        {
            List<ListenerEvent> listenerEventList = getEventListener(event);
            for (final ListenerEvent listenerEvent : listenerEventList)
            {
                if (executorService != null)
                {
                    executorService.execute(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (logger.isInfoEnabled())
                            {
                                logger.info("开始异步处理事件[" + event.getClass().getName() + "]......");
                            }
                            listenerEvent.onEvent(event);
                        }
                    });
                }
                else
                {
                    if (logger.isInfoEnabled())
                    {
                        logger.info("开始同步处理事件[" + event.getClass().getName() + "]......");
                    }
                    listenerEvent.onEvent(event);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("处理事件[" + event.getClass().getName() + "]发生错误!");
        }
    }

    /**
     * 获取相应的事件监听器
     *
     * @param event
     * @return
     */
    protected List<ListenerEvent> getEventListener(triggerEvent event)
    {
        Class<? extends triggerEvent> eventClass = event.getClass();
        if (!cachedEventListeners.containsKey(eventClass))
        {
            LinkedList<ListenerEvent> listeners = new LinkedList<ListenerEvent>();
            if (eventListenerSet != null && eventListenerSet.size() > 0)
            {
                for (ListenerEvent listenerEvent : eventListenerSet)
                {
                    if (judgeEvent(listenerEvent, eventClass))
                    {
                        listeners.add(listenerEvent);
                    }
                }
                sortEventListener(listeners);
            }
            ListenerRegistry listenerRegistry = new ListenerRegistry(listeners);
            cachedEventListeners.put(eventClass, listenerRegistry);
        }
        return cachedEventListeners.get(eventClass).getListenerEventList();
    }

    /**
     * 判断监听器类型和事件类型是否匹配
     *
     * @param listenerEvent
     * @param eventClass
     * @return
     */
    protected boolean judgeEvent(ListenerEvent listenerEvent, Class<? extends triggerEvent> eventClass)
    {
        Class typeArg = GenericTypeResolver.resolveTypeArgument(listenerEvent.getClass(), ListenerEvent.class);
        if (typeArg == null || typeArg.equals(triggerEvent.class))
        {
            Class targetClass = AopUtils.getTargetClass(listenerEvent);
            if (targetClass != listenerEvent.getClass())
            {
                typeArg = GenericTypeResolver.resolveTypeArgument(targetClass, ListenerEvent.class);
            }
        }
        return (typeArg == null || typeArg.isAssignableFrom(eventClass));
    }

    protected void sortEventListener(LinkedList<ListenerEvent> listenerEvents)
    {
        Collections.sort(listenerEvents, new Comparator<ListenerEvent>()
        {
            public int compare(ListenerEvent listenerEvent, ListenerEvent listenerEvent1)
            {
                if (listenerEvent.getOrder() > listenerEvent1.getOrder())
                {
                    return 1;
                }
                else if (listenerEvent.getOrder() < listenerEvent1.getOrder())
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        });
    }

    public void setExecutorService(ExecutorService executorService)
    {
        this.executorService = executorService;
    }

    private class ListenerRegistry
    {
        public List<ListenerEvent> listenerEvents;

        private ListenerRegistry(List<ListenerEvent> listenerEvents)
        {
            this.listenerEvents = listenerEvents;
        }

        public List<ListenerEvent> getListenerEventList()
        {
            return listenerEvents;
        }
    }
}


/**
 * 事件监听者接口
 */
interface ListenerEvent
{
    void onEvent(triggerEvent baseEvent);

    int getOrder();
}


/**
 * 事件执行者
 */
class triggerEvent extends EventObject
{

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public triggerEvent(Object source)
    {
        super(source);
    }
}
