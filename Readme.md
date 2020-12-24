## Как запустить приложение

* ` docker pull zoltannz/hadoop-ubuntu:2.8.1`

* `Загрузка образа из регистра`

* ` docker run --name hadoop-psql -p 2122:2122 -p 8020:8020 -p 8030:8030 -p 8040:8040`
   `-p 8042:8042 -p  8088:8088 -p 9000:9000 -p 10020:10020 -p 19888:19888 -p 49707:49707`
   `-p 50010:50010 -p 50020:50020 -p 50070:50070 -p 50075:50075 -p 50090:50090 `
   `-t zoltannz/hadoop-ubuntu:2.8.1`
   
* `Запуск контейнера с именем  hadoop-psql с портами`
* В новом терминале в директории hw2/report выполнить `./startHadoopContainer.sh hadoop-psql` 

### Успешные тесты
![Tests](report/tests.jpg)

### Успешная работа
![Job](report/ResultJob.jpg)

