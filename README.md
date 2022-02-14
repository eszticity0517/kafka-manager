# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Simple Kafka message producer and consumer project, with topic creation included.

### How do I get set up? ###

Please use Linux Ubuntu.

* apt update
* apt install default-jdk
* java --version should print 11 in terminal
* adduser kafka
* adduser kafka sudo
* su -l kafka to login with this user
* mkdir ~/Downloads
* apt install curl
* curl " https://dlcdn.apache.org/kafka/3.1.0/kafka_2.13-3.1.0.tgz" -o ~/Downloads/kafka.tgz
* mkdir ~/kafka && cd ~/kafka
* tar -xvzf ~/Downloads/kafka.tgz --strip 1
* nano ~/kafka/config/server.properties file should be extended with this line: delete.topic.enable = true
* change this line in the above file: log.dirs to not use tmp, for example: /home/kafka/logs
* sudo nano /etc/systemd/system/zookeeper.service
put this into the file:
[Unit]
Requires=network.target remote-fs.target
After=network.target remote-fs.target

[Service]
Type=simple
User=kafka
ExecStart=/home/kafka/kafka/bin/zookeeper-server-start.sh /home/kafka/kafka/config/zookeeper.properties
ExecStop=/home/kafka/kafka/bin/zookeeper-server-stop.sh
Restart=on-abnormal

[Install]
WantedBy=multi-user.target
* sudo nano /etc/systemd/system/kafka.service
put this into the file:
[Unit]
Requires=zookeeper.service
After=zookeeper.service

[Service]
Type=simple
User=kafka
ExecStart=/bin/sh -c '/home/kafka/kafka/bin/kafka-server-start.sh /home/kafka/kafka/config/server.properties > /home/kafka/kafka/kafka.log 2>&1'
ExecStop=/home/kafka/kafka/bin/kafka-server-stop.sh
Restart=on-abnormal

[Install]
WantedBy=multi-user.target
* sudo systemctl start kafka
* sudo systemctl status kafka should show active status
* check /home/kafka/kafka/kafka.log file if error occurs