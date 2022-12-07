import time
from kafka import KafkaConsumer

class MyKafkaConsumer():
    def __init__(self):


    def listen(self):
        try: 
            records = self.consumer.poll(1.0)
            for topic_data, consumer_records in records.items():
                for consumer_record in consumer_records:
                    key = str(consumer_record.key.decode('utf-8'))
                    value = str(consumer_record.value.decode('utf-8'))
                    return key,value
        except KeyboardInterrupt:
            return None,None