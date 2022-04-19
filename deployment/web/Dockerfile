FROM payara/server-web:5.2021.4-jdk11

EXPOSE 4848 9009 8080 8181

ENV DB_USER=postgres
ENV DB_PWD=postgres
ENV DB_HOST=localhost

ENV COMMON_LIB_DIR ${PAYARA_DIR}/glassfish/domains/${DOMAIN_NAME}/lib
COPY --chown=payara:payara target/provided-dependencies ${COMMON_LIB_DIR}/

COPY target/book-store.war $DEPLOY_DIR