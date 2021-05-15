http://localhost:8000/api/jobs/d22f39dc-6aaf-453f-b138-fc217e6a65a4

run in cloud:
java -Dexec.mainClass=MainApp \
-Dexec.args="--project=car-dataflow  \
--project=car-dataflow \
--bucket=car-dataflow \
--functionBase=https://europe-west3-car-dataflow.cloudfunctions.net


mvn compile exec:java -Dexec.mainClass=MainApp -Dexec.args="--project=car-dataflow --bucket=car-dataflow --functionBase=https://europe-west3-car-dataflow.cloudfunctions.net --gcpTempLocation=gs://car-dataflow/tmp --region=us-east1 --runner=DataflowRunner"

