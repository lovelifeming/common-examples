package com.zsm.commonexample.event;

import org.apache.log4j.Logger;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.GenericTypeResolver;

import java.util.*;
import java.util.concurrent.ExecutorService;


public class EventMultiCaster
{

    private static final Logger logger = Logger.getLogger(EventMultiCaster.class);

    private ExecutorService executorService;

    /**
     * 事件监听器存储容器
     */
    private Set<EventListener> eventListenerSet = new HashSet<EventListener>();

    private Map<Class<? extends FOPEvent>, ListenerRegistry> cachedEventListeners = new HashMap<Class<? extends FOPEvent>, ListenerRegistry>();

    /**
     * 注册监听器
     *
     * @param fopEventListener
     */
    public void addFOPListener(EventListener fopEventListener)
    {

        eventListenerSet.add(fopEventListener);
    }

    /**
     * 删除监听器
     *
     * @param fopEventListener
     */
    public void removeFOPListener(EventListener fopEventListener)
    {

        eventListenerSet.remove(fopEventListener);
    }

    /**
     * 清除所有注册的监听器
     */
    public void removeAllFOPListeners()
    {

        eventListenerSet.clear();
    }

    /**
     * 组播事件给相应的注册监听器 - 异步执行
     *
     * @param event
     */
    public void multicastEvent(final FOPEvent event)
    {

        try
        {

            List<EventListener> fopEventListenerList = getEventListener(event);
            for (final EventListener fopEventListener : fopEventListenerList)
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

                            fopEventListener.onEvent(event);
                        }
                    });

                }
                else
                {

                    if (logger.isInfoEnabled())
                    {
                        logger.info("开始同步处理事件[" + event.getClass().getName() + "]......");
                    }

                    fopEventListener.onEvent(event);
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
    protected List<EventListener> getEventListener(FOPEvent event)
    {

        Class<? extends FOPEvent> eventClazz = event.getClass();
        if (!cachedEventListeners.containsKey(eventClazz))
        {
            LinkedList<EventListener> listeners = new LinkedList<EventListener>();

            if (eventListenerSet != null && eventListenerSet.size() > 0)
            {

                for (EventListener fopEventListener : eventListenerSet)
                {

                    if (supportedEvent(fopEventListener, eventClazz))
                    {
                        listeners.add(fopEventListener);
                    }
                }

                sortEventListener(listeners);
            }
            ListenerRegistry listenerRegistry = new ListenerRegistry(listeners);
            cachedEventListeners.put(eventClazz, listenerRegistry);
        }

        return cachedEventListeners.get(eventClazz).getFopEventListenerList();
    }

    /**
     * 判断监听器类型和事件类型是否匹配
     *
     * @param fopEventListener
     * @param eventClazz
     * @return
     */
    protected boolean supportedEvent(EventListener fopEventListener, Class<? extends FOPEvent> eventClazz)
    {

        Class typeArg = GenericTypeResolver.resolveTypeArgument(fopEventListener.getClass(), EventListener.class);

        if (typeArg == null || typeArg.equals(FOPEvent.class))
        {
            Class targetClass = AopUtils.getTargetClass(fopEventListener);
            if (targetClass != fopEventListener.getClass())
            {
                typeArg = GenericTypeResolver.resolveTypeArgument(targetClass, EventListener.class);
            }
        }

        return (typeArg == null || typeArg.isAssignableFrom(eventClazz));
    }

    protected void sortEventListener(LinkedList<EventListener> fopEventListeners)
    {

        Collections.sort(fopEventListeners, new Comparator<EventListener>()
        {

            public int compare(EventListener fopEventListener1, EventListener fopEventListener2)
            {
                if (fopEventListener1.getOrder() > fopEventListener2.getOrder())
                {
                    return 1;
                }
                else if (fopEventListener1.getOrder() < fopEventListener2.getOrder())
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

    /**
     * inner Class
     */
    private class ListenerRegistry
    {

        public List<EventListener> fopEventListenerList;

        private ListenerRegistry(List<EventListener> fopEventListenerList)
        {
            this.fopEventListenerList = fopEventListenerList;
        }

        public List<EventListener> getFopEventListenerList()
        {
            return fopEventListenerList;
        }
    }
}
class EventListener
{

}
