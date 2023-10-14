pipeline {
    agent any
    tools{
        jdk "JAVA_HOME"
        maven "MAVEN"
        }
    stages {
        stage('Test') {
            steps {
                //bat "mvn -D clean test"  
                bat "mvn -D clean verify sonar:sonar -Dsonar.token=sqa_73a5151e0db420867465e0c8631031e964c15d85"
                //publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '', reportFiles: 'test- 
                output/emailable-report.html', reportName: 'HTML Report', reportTitles: '', useWrapperFileDirectly: true])

          }
        }
    }
    post {  
         always {  
               //archiveArtifacts artifacts: 'DataFiles/*', followSymlinks: false
               archiveArtifacts artifacts: 'test-output/*.html', followSymlinks: false
               emailext attachmentsPattern: 'test-output\\*.html', body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:Check 
            console output at $BUILD_URL to view the results.''', compressLog: true, mimeType: 'text/html', subject: '$PROJECT_NAME - 
             Build # $BUILD_NUMBER - $BUILD_STATUS!', to: 'pravin.kumar@interactiveavenues.com'
               //emailext attachmentsPattern: 'DataFiles/*', body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:Check console 
               output at $BUILD_URL to view the results.''', compressLog: true, mimeType: 'text/html', subject: '$PROJECT_NAME - Build # 
               $BUILD_NUMBER - $BUILD_STATUS!', to: 'pravin.kumar@interactiveavenues.com' 
               
                 }  
    }   

}
