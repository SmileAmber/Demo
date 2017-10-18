// Declarative //
pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                echo 'Building..'
                sh 'cd webdemo && ./gradlew build'
            }
        }

        stage('Test') {
            steps {
                echo 'Testing..'
                sh 'cd webdemo && ./gradlew build || true'
              //  junit '**/target/*.xml'
            }
        }

        
        
        stage('SonarQube analysis') {
           steps {
               echo "starting codeAnalyze with SonarQube......"
               script{
               def sonarqubeScannerHome = tool name:'SonarScannerTest'
               withSonarQubeEnv('SonarSeverTest') {
                 //固定使用项目根目录${basedir}下的pom.xml进行代码检查
                   sh "${sonarqubeScannerHome}/bin/sonar-scanner"
               }
            
               timeout(4) { 
                   //利用sonar webhook功能通知pipeline代码检测结果，未通过质量阈，pipeline将会fail
                   def qg = waitForQualityGate() 
                       if (qg.status != 'OK') {
                           error "未通过Sonarqube的代码质量阈检查，请及时修改！failure: ${qg.status}"
                       }
                   }
               }
           }
       }
        
        stage('Deploy') {
          //  when {
             //   expression {
                    /*如果测试失败，状态为UNSTABLE*/
               //     currentBuild.result == 'SUCCESS'
            //   }
         //   }
            steps {
                echo 'Deploying..'
         
                //sh 'ssh -tt hbao@10.209.21.215 < deploy.sh'
                
                sh """
                set -e
ssh hbao@10.209.21.215 '
tomcat_path = /Users/hbao/Downloads/apache-tomcat-7.0.82
TomcatID = $(ps -ef |grep tomcat |grep -w $tomcat_path|grep -v 'grep'|awk '{print $2}')
                
StopTomcat = /Users/hbao/Downloads/apache-tomcat-7.0.82/bin/shutdown.sh
                
if[($TomcatID)]; then
   echo "当前Tomcat进程ID为：$TomcatID, 需要关闭..."
   $StopTomcat
else
   echo "当前环境没有Tomcat启动"
fi
                   
rm -rf /Users/hbao/Downloads/apache-tomcat-7.0.82/webapps/webdemo
rm -f /Users/hbao/Downloads/apache-tomcat-7.0.82/webapps/webdemo.war
'
                
cd /var/jenkins_home/workspace/TestForPipeline/webdemo/build/libs
                
scp webdemo.war hbao@10.209.21.215:/Users/hbao/Downloads/apache-tomcat-7.0.82/webapps
ssh hbao@10.209.21.215 '
cd /Users/hbao/Downloads/apache-tomcat-7.0.82/bin
./startup.sh
'
                """
                
            }
        }
        
    }
}

