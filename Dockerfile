FROM payara/micro:5.2022.1-jdk11

EXPOSE 6900 8080

# DB configuration
ENV DB_USER=postgres
ENV DB_PWD=postgres
ENV DB_HOST=localhost

# External libraries
ENV LIB_DIR ${PAYARA_DIR}/lib
RUN mkdir -p ${LIB_DIR}
COPY --chown=payara:payara target/provided-dependencies ${LIB_DIR}/

# Deployment
RUN mkdir -p ${DEPLOY_DIR}
COPY target/book-store.war $DEPLOY_DIR

# Boot
CMD ["--deploymentDir", "/opt/payara/deployments", "--addLibs", "/opt/payara/lib"]