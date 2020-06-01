Feature: assertCommandExistsOnTimeOutIT - Check command output with exit status

  Scenario: Check command output with correct expresion and exist status=0
    Given I set sso token using host '!{EOS_ACCESS_POINT}' with user '${DCOS_USER}' and password '${DCOS_PASSWORD}' and tenant 'NONE'
    Then I get container name for task 'pg-0001' in service with id '/s000001/s000001-postgrestls2' and save the value in environment variable 'pgContainerName'
    And I run 'echo '!{pgContainerName}'' locally
    Then I get container name for task '.*' in service with id '/s000001/rocket-carlos/s000001-rocket-carlos' and save the value in environment variable 'rocketContainerName'
    And I run 'echo '!{rocketContainerName}'' locally