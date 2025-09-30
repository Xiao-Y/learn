# 使用更稳定的 JDK 镜像（避免 Alpine 中的字体问题）
FROM adoptopenjdk/openjdk8:latest
# 作者
MAINTAINER xiao-y <lyongtao123@126.com>

# 设置工作目录
WORKDIR /app

# 将构建好的 jar 包复制到容器中
COPY *.jar app.jar

# 暴露服务端口
EXPOSE 8811

## 启动命令，加入 headless 模式支持
#ENTRYPOINT ["java", \
#    "-jar", \
#    "app.jar"]
# 运行jar包
RUN bash -c 'touch app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]