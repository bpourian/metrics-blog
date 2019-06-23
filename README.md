## Metrics-Blog

What is latency and why do we we want to measure it?

Latency is the time between a user invoked action and the resulting response. 
It gives us an expression of how much time it takes for a packet of data to travel within a network.

To measure latency I will be using Prometheus as it offers a range of options.
I have provided a very simple demonstration in a unit test on how you could gather data by adding simple 
Prometheus methods to your code in this repo.

Please bear in mind that there is a lot more to measuring latency than relying on a 
few methods to log invocation and response times. And there are plenty more ways to display that data. 
I would recommend watching a talk by "Gil Tene" [here](https://www.youtube.com/watch?v=lJ8ydIuPFeU&feature=youtu.be).

Prometheus has a range of metrics collection methods. For this particular scenario we will be using `Histogram` which 
is commonly used to gather latency data.

In the example below I am simply importing the dependency and building a Histogram like so: 

````
Histogram myHistogram =  Histogram.build()
                         .name("API_REQUEST_LATENCY_MILLIS")
                         .help("REQUEST_LATENCY_IN_MILLISECONDS")
                         .labelNames("METHOD")
                         .buckets(0.5D, 1D, 2D, 3D, 4D)
                         .register();
````

Here, I am declaring a new Histogram with one label called `method`. Now the interesting part 
is the buckets .

Buckets, as the name suggests collect data and provide you with a count. Using the prometheus 
api you can easily access this data and display it on something like `Grafana`.

The above buckets are labelled in milliseconds and represent a double data type. Simply put, the first bucket will 
collect all the calls that have been made and completed in half a second and so forth.

Once you have instantiated a `Histogram` object then you can start a specific timer with a label like so:

``````
Histogram.timer myTimer = myHistogram.labels(method.toString())
                           .startTimer();
``````
                           
                           
This is followed by a method which observes the time that has lapsed and counts it against the bucket you have declared.

``````
myTimer.observeDuration();
``````

So now every time your code gets called the Prometheus timer will start logging.
