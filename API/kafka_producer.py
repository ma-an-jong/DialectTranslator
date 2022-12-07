from kafka import KafkaProducer
from json import dumps

class MyKafkaProducer():
    def __init__(self):
        self.topic_name = 'java-topic'
        self.__producer = KafkaProducer(acks=0,
                                        compression_type='gzip',
                                        bootstrap_servers=['202.31.202.34:80'])
#                                        key_serializer=str.encode,
#                                        value_serializer=str.encode)
        print(self.__producer)
        
    def send(self,key,value):
        print(key,value)
        self.__producer.send(self.topic_name,key=key.encode('utf-8'),value=value.encode('utf-8'))
        self.__producer.flush()
