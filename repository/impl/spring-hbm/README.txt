Para que puedan correr los test tienen que hacerlo con un profile donde esta la configuracion. El profile debe contener lo siguiente:

  <profile>
    <id>spring-repository-impl</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <properties>

      <!-- the datasource for test url. -->
      <spring-repository-impl.dataSource.url><URL></spring-repository-impl.dataSource.url>
      <spring-repository-impl.dataSource.driverClassName><DRIVER></spring-repository-impl.dataSource.driverClassName>
      <spring-repository-impl.dataSource.username><USERNAME></spring-repository-impl.dataSource.username>
      <spring-repository-impl.dataSource.password><PASSWORD></spring-repository-impl.dataSource.password>

      <spring-repository-impl.hibernate.dialect><DIALECT></spring-repository-impl.hibernate.dialect>
      <spring-repository-impl.hibernate.hbm2ddl.auto>(none|update|create)</spring-repository-impl.hibernate.hbm2ddl.auto>
      <spring-repository-impl.hibernate.show_sql>(false|true)</spring-repository-impl.hibernate.show_sql>

      <spring-repository-impl.log4j.debugLevel>(debug|info|warn|error|fatal)</spring-repository-impl.log4j.debugLevel>
      <spring-repository-impl.log4j.appender>(console|logFile)</spring-repository-impl.log4j.appender>

    </properties>
  </profile>
