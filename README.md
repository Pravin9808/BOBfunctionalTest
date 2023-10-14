# BOBfunctionalTest
Reprt file ipeline
pipeline {
    agent any
    tools{
        jdk "JAVA_HOME"
        maven "MAVEN"
        }
    stages {
        stage('Test') {
            steps {
                bat "mvn -D clean test"  
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '', reportFiles: 'test-output/emailable-report.html', reportName: 'HTML Report', reportTitles: '', useWrapperFileDirectly: true])

          }
        }
    }
    post {  
         always {  
               //archiveArtifacts artifacts: 'test-output/*html', followSymlinks: false
               //emailext attachmentsPattern: 'test-output\\*html', body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:Check console output at $BUILD_URL to view the results.''', compressLog: true, mimeType: 'text/html', subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!', to: 'pravin.kumar@interactiveavenues.com'
               emailext attachmentsPattern: 'DataFiles', body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:Check console output at $BUILD_URL to view the results.''', compressLog: true, mimeType: 'text/html', subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!', to: 'pravin.kumar@interactiveavenues.com'

// emailext attachLog: true, attachmentsPattern: 'test-output/emailable-report.html', mimeType: 'text/html',
               //body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n Link: ${env.BUILD_URL}",
               //recipientProviders: [developers(), requestor()],
               //subject: "Jenkins Build Report  ${currentBuild.currentResult}: Job ${env.JOB_NAME}"                        
               //to: "pravin.kumar@interactiveavenues.com"
             //def console_output = "${env.BUILD_URL}/console" 
               // emailext (subject: "STARTED: jOB '${env.JOB_NAME} [${env.BUILD_NUMBER}]' RESULT: ${currentBuild.currentResult}",
                      //  attachLog: true, attachmentsPattern: "**/${WORKSPACE}/DataFiles.zip",compressLog: true,
                      //  body: "Check console output at   ${env.BUILD_URL}" ,
                       // from:"pravininteractive25@gmail.com",            
                       // to: "pravin.kumar@interactiveavenues.com");
                 }  
    }   

}
