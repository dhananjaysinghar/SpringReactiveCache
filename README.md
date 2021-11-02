# SpringReactiveCache - Caffeine
### High-performance caching library for Java. One fundamental difference between a cache and a Map is that a cache evicts stored items based on configurations.


~~~
<dependency>
	 <groupId>com.github.ben-manes.caffeine</groupId>
	<artifactId>caffeine</artifactId>
</dependency>
<dependency>
	<groupId>io.projectreactor.addons</groupId>
	<artifactId>reactor-extra</artifactId>
</dependency>
~~~

~~~
spring:
  cache:
    cache-names: TOKEN_CACHE
    caffeine:
      spec: maximumSize=5000,expireAfterWrite=60m,recordStats # expire in 60 min
~~~
