curl -X GET "http://localhost:5000/task"  -H 'accept: */*'
curl -X DELETE "http://localhost:5000/task/2"  -H 'accept: */*'  -H 'Authorization: Basic QWRtaW46YWRtaW4='
curl -X PUT   "http://localhost:5000/task/{id}/status"   -H 'accept: */*'   -H 'Content-Type: application/json'   -d '{  "id": 1,  "taskDescription": "string",  "targetDate": "2023-08-27",  "state": "string",  "event": "START",  "extendedState": "string"}'
curl -X DELETE  "http://sp17-env.eba-e9h7aw33.eu-central-1.elasticbeanstalk.com/task/1"  -H 'accept: */*'  -H 'Authorization: Basic QWRtaW46YWRtaW4='
curl -X GET  "http://sp17-env.eba-e9h7aw33.eu-central-1.elasticbeanstalk.com/task"  -H 'accept: */*'  -H 'Authorization: Basic QWRtaW46YWRtaW4='
curl -X 'GET' \
  'http://localhost:5000/' \
  -H 'accept: */*'