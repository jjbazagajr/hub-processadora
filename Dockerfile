FROM openjdk:11

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}
ENV TZ America/Recife

WORKDIR /opt/starter

COPY /target/hub-avaliacao*.jar hub-avaliacao.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 8080
EXPOSE 5005

CMD java ${ADDITIONAL_OPTS} -jar hub-avaliacao.jar --spring.profiles.active=${PROFILE}