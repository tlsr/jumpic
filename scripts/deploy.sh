
scp -i C:/Users/Antihrist/sshkey.ppk C:/Users/Antihrist/IdeaProjects/jumpic/target/jumping-1.0-SNAPSHOT.jar antihrist@192.168.0.102:/home/antihrist
ssh -i C:/Users/Antihrist/sshkey.ppk antihrist@192.168.0.102 << 'ENDSSH'
pgrep java | xargs kill -9
nohup java -jar jumping-1.0-SNAPSHOT.jar
ENDSSH