# Java同步学习代码实例


**安装jdk**
安装
```
tar -zxvf jdk-8u161-linux-x64.tar.gz 
sudo cp -r jdk1.8.0_161 /usr/local/
```
修改配置
```
sudo apt install vim
sudo vim /etc/profile
```
末尾追加：
```cmd
JAVA_HOME=/usr/local/jdk1.8.0_161
JRE_HOME=/usr/local/jdk1.8.0_161/jre
CLASS_PATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib
PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin
export JAVA_HOME JRE_HOME CLASS_PATH PATH

```
生效
```
hanchao@hanchao-virtual-machine:/etc$ echo $PATH
/home/hanchao/bin:/home/hanchao/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
hanchao@hanchao-virtual-machine:/etc$ source /etc/profile
hanchao@hanchao-virtual-machine:/etc$ echo $PATH
/home/hanchao/bin:/home/hanchao/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:/snap/bin:/usr/local/jdk1.8.0_161/bin:/usr/local/jdk1.8.0_161/jre/bin
```

永久生效：重启

查看
```
hanchao@hanchao-virtual-machine:/etc$ java -version
java version "1.8.0_161"
Java(TM) SE Runtime Environment (build 1.8.0_161-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.161-b12, mixed mode)

```

**安装tomcat**
解压存放
```
unzip -q apache-tomcat-7.0.82.zip 
sudo cp -r apache-tomcat-7.0.82 /usr/local/tomcat
rm -rf apache-tomcat-7.0.82 
sudo mv tomcat apache-tomcat-7.0.82

```
查看接口是否被占用：创建root密码并切换至root用户
```
hanchao@hanchao-virtual-machine:/usr/local$ netstat -apn|grep 8080
（并非所有进程都能被检测到，所有非本用户的进程信息将不会显示，如果想看到所有信息，则必须切换到 root 用户）
hanchao@hanchao-virtual-machine:/usr/local$ su
密码： 
su：认证失败
hanchao@hanchao-virtual-machine:/usr/local$ sudo passwd root
输入新的 UNIX 密码： 
重新输入新的 UNIX 密码： 
passwd：已成功更新密码
hanchao@hanchao-virtual-machine:/usr/local$ su - root
密码： 
root@hanchao-virtual-machine:~# netstat -apn|grep 8080

```
启动tomcat
```
root@hanchao-virtual-machine:/usr/local/apache-tomcat-7.0.82/bin# ./startup.sh
-su: ./startup.sh: 权限不够
root@hanchao-virtual-machine:/usr/local/apache-tomcat-7.0.82/bin# ll
总用量 816
drwxr-xr-x 2 root root   4096 4月   4 10:15 ./
drwxr-xr-x 9 root root   4096 4月   4 10:15 ../
-rw-r--r-- 1 root root  28503 4月   4 10:15 bootstrap.jar
-rw-r--r-- 1 root root  14028 4月   4 10:15 catalina.bat
-rw-r--r-- 1 root root  21646 4月   4 10:15 catalina.sh
-rw-r--r-- 1 root root   1686 4月   4 10:15 catalina-tasks.xml
-rw-r--r-- 1 root root  24283 4月   4 10:15 commons-daemon.jar
-rw-r--r-- 1 root root 204944 4月   4 10:15 commons-daemon-native.tar.gz
-rw-r--r-- 1 root root   2040 4月   4 10:15 configtest.bat
-rw-r--r-- 1 root root   1922 4月   4 10:15 configtest.sh
-rw-r--r-- 1 root root   8157 4月   4 10:15 daemon.sh
-rw-r--r-- 1 root root   2091 4月   4 10:15 digest.bat
-rw-r--r-- 1 root root   1965 4月   4 10:15 digest.sh
-rw-r--r-- 1 root root   3430 4月   4 10:15 setclasspath.bat
-rw-r--r-- 1 root root   3547 4月   4 10:15 setclasspath.sh
-rw-r--r-- 1 root root   2020 4月   4 10:15 shutdown.bat
-rw-r--r-- 1 root root   1902 4月   4 10:15 shutdown.sh
-rw-r--r-- 1 root root   2022 4月   4 10:15 startup.bat
-rw-r--r-- 1 root root   1904 4月   4 10:15 startup.sh
-rw-r--r-- 1 root root  44739 4月   4 10:15 tomcat-juli.jar
-rw-r--r-- 1 root root 404159 4月   4 10:15 tomcat-native.tar.gz
-rw-r--r-- 1 root root   4021 4月   4 10:15 tool-wrapper.bat
-rw-r--r-- 1 root root   5024 4月   4 10:15 tool-wrapper.sh
-rw-r--r-- 1 root root   2026 4月   4 10:15 version.bat
-rw-r--r-- 1 root root   1908 4月   4 10:15 version.sh

root@hanchao-virtual-machine:/usr/local/apache-tomcat-7.0.82/bin# chmod +x *.sh
root@hanchao-virtual-machine:/usr/local/apache-tomcat-7.0.82/bin# ./startup.sh 
Using CATALINA_BASE:   /usr/local/apache-tomcat-7.0.82
Using CATALINA_HOME:   /usr/local/apache-tomcat-7.0.82
Using CATALINA_TMPDIR: /usr/local/apache-tomcat-7.0.82/temp
Using JRE_HOME:        /usr/local/jdk1.8.0_161/jre
Using CLASSPATH:       /usr/local/apache-tomcat-7.0.82/bin/bootstrap.jar:/usr/local/apache-tomcat-7.0.82/bin/tomcat-juli.jar
Tomcat started.

```

**把tomcat做成服务**
复制配置
```
cp /usr/java/tomcat/bin/catalina.sh /etc/init.d/tomcat    #重命名的tomcat为以后的服务名
```
修改配置
```
### BEGIN INIT INFO
# Provides:          tomcat
# Required-Start:    $remote_fs $network
# Required-Stop:     $remote_fs $network
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: The tomcat Java Application Server
### END INIT INFO

CATALINA_HOME=/usr/local/apache-tomcat-7.0.82
JAVA_HOME=/usr/local/jdk1.8.0_161
```
配置文件授权

```
chmod 755 /etc/init.d/tomcat

```
在Ubuntu中是没有chkconfig命令的，可以用update-rc.d 来代替。
```
sudo update-rc.d tomcat defaults
```
启动服务
```
root@hanchao-virtual-machine:/etc/init.d# sudo service tomcat start

```


**solr老版本下载地址**

http://archive.apache.org/dist/lucene/solr/

http://archive.apache.org/dist/lucene/solr/6.6.0/

solr-6.6.0.zip

`unzip -q solr-6.6.0.zip`

**solr安装**

usr/local/solr/solr-6.6.0

```shell
hanchao@hanchao-virtual-machine:/usr$ mv /home/hanchao/solr-6.6.0/ /usr/local/
mv: 无法将'/home/hanchao/solr-6.6.0/' 移动至'/usr/local/solr-6.6.0': 权限不够

hanchao@hanchao-virtual-machine:/usr$ sudo mv /home/hanchao/solr-6.6.0/ /usr/local/
```

**solr服务启动**

```
hanchao@hanchao-virtual-machine:~$ /usr/local/solr-6.6.0/bin/solr start
Waiting up to 180 seconds to see Solr running on port 8983 [\]  
Started Solr server on port 8983 (pid=2404). Happy searching!

```

**solr修改时区**
默认时区：
Java Properties-->user.timezone = UTC
修改solr/bin/solr.in.sh linux      solr.in.cmd=windows

```
#SOLR_TIMEZONE="UTC"
SOLR_TIMEZONE="UTC+8"
./solr restart
```


