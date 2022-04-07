FROM jenkins/jenkins:2.303.3-jdk11

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

COPY seedJob.xml /usr/share/jenkins/ref/jobs/seed-job/config.xml

#ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
#88e56583000d4486bb2339ab840cdcf6