FROM openjdk:11-jre-slim
ARG AUTH_KEY
COPY ./build/libs/bot.jar app.jar
EXPOSE 8081
ARG ARG_AUTH_KEY
ENV AUTH_KEY=${ARG_AUTH_KEY}
ENTRYPOINT java -DauthKey=$AUTH_KEY -jar /app.jar