getMeanSDN<-function(x){
	data.frame(mean=mean(x),sd=sd(x),N=length(x))
}