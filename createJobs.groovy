pipelineJob('pipelineJob') {
    definition {
        cps {
            script(readFileFromWorkspace('pipelineJob.groovy'))
            sandbox()
        }
    }
}
pipelineJob('budgetyJob') {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url 'https://github.com/frivolta/budgety-v6-api'
                    }
                    branch 'main'
                }
            }
        }
    }
}