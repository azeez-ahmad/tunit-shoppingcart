FROM openjdk:17-alpine

LABEL description="This is a docker image for the shopping cart"

RUN apk add --no-cache tini

WORKDIR /usr/local/server

COPY target/*.jar ./tunit-shoppingcart.jar

EXPOSE 8080

ENTRYPOINT ["/sbin/tini","-g","--"]

CMD ["sh","-c","java ${JAVA_OPTS} -jar tunit-shoppingcart.jar"]

