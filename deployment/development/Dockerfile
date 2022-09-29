FROM payara/server-full:5.2022.2-jdk11

EXPOSE 4848 9009 8080 8181 4900

ENV DB_USER=postgres
ENV DB_PWD=postgres
ENV DB_HOST=localhost


RUN echo 'set-hazelcast-configuration --enabled=true --dynamic=true --clustermode multicast --multicastgroup 224.2.2.4 --multicastport 55000 --clustername book-store-grid --membergroup book-store-group' > $POSTBOOT_COMMANDS

ENV COMMON_LIB_DIR ${PAYARA_DIR}/glassfish/domains/${DOMAIN_NAME}/lib
COPY --chown=payara:payara deps/ ${COMMON_LIB_DIR}/