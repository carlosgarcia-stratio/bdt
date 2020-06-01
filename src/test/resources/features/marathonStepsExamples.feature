Feature: assertCommandExistsOnTimeOutIT - Check command output with exit status

  Scenario: Check command output with correct expresion and exist status=0
    Given I set sso token using host '!{EOS_ACCESS_POINT}' with user '${DCOS_USER}' and password '${DCOS_PASSWORD}' and tenant 'NONE'
    Then I get environment variable 'HAPROXY_HOST' for service with id '/s000001/rocket-carlos/s000001-rocket-test' and save the value in environment variable 'proxyHost'
    And I run 'echo '!{proxyHost}'' locally
    Then I get label 'HAPROXY_0_PATH' for service with id '/s000001/rocket-carlos/s000001-rocket-test' and save the value in environment variable 'proxyPath'
    And I run 'echo '!{proxyPath}'' locally
    Then in less than '100' seconds, checking each '5' seconds, service with id '/s000001/rocket-carlos/s000001-rocket-test' has '1' task in 'running' state in Marathon