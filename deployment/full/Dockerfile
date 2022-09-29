FROM payara/server-full:5.2022.2-jdk11

EXPOSE 4848 9009 8080 8181 4900

ENV DB_USER=postgres
ENV DB_PWD=postgres
ENV DB_HOST=localhost

USER root
RUN apt-get update && apt-get install -y curl
USER payara

RUN echo 'set-hazelcast-configuration --enabled=true --dynamic=true --clustermode multicast --clustername book-store-grid --membergroup book-store-group' > $POSTBOOT_COMMANDS

ENV COMMON_LIB_DIR ${PAYARA_DIR}/glassfish/domains/${DOMAIN_NAME}/lib
COPY --chown=payara:payara target/provided-dependencies ${COMMON_LIB_DIR}/

ENV ARCHIVE_DIR ${PAYARA_DIR}/archive
RUN mkdir ${ARCHIVE_DIR}

RUN curl --output ${ARCHIVE_DIR}/artemis-rar.rar https://repo1.maven.org/maven2/org/apache/activemq/examples/modules/artemis-jakarta-rar/2.26.0/artemis-jakarta-rar-2.26.0.rar
COPY target/book-store.war $ARCHIVE_DIR

RUN echo deploy --type rar ${ARCHIVE_DIR}/artemis-rar.rar >> ${POSTBOOT_COMMANDS}

RUN echo 'create-resource-adapter-config  --property ConnectorClassName="org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory":ConnectionParameters="host=artemis;port=61616":UserName="artemis":Password="artemis" artemis-rar' >> ${POSTBOOT_COMMANDS}
RUN echo 'create-connector-connection-pool --raname artemis-rar --connectiondefinition org.apache.activemq.artemis.ra.ActiveMQRAConnectionFactory --ping true jms/ConnectionFactoryPool' >> ${POSTBOOT_COMMANDS}
RUN echo 'create-connector-resource --poolname jms/ConnectionFactoryPool jms/myConnectionFactory' >> ${POSTBOOT_COMMANDS}

RUN echo deploy ${ARCHIVE_DIR}/book-store.war >> ${POSTBOOT_COMMANDS}