import torch
import torch.nn.functional as F
from model import Translators
from classifier import DialectClassifier
from kafka import KafkaConsumer
from kafka_producer import MyKafkaProducer
import time

def translate_response(key,value):
    if key == None and value ==None:
        return {"except" : "null"}
    try:
        if key == None:
            label = classifier.classification(value)
        else:
            label = key
            
        print(label,models.dict[label])
        
        standard_form =models.get_response(content = value,index = label)
        region =  models.dict[label]
        
        return standard_form,region
    
    except IndexError:
        return {"except" : "unvalid labeling"}

bootstrap_server = get_bootstrap_server()
producer = MyKafkaProducer()

consumer = KafkaConsumer(
        bootstrap_servers=[bootstrap_server],
                  auto_offset_reset='earliest',
                       group_id='my-consumer-1',
                       )

consumer.subscribe(['testtopic'])

classifier = DialectClassifier()
classifier.load_state_dict(torch.load('models/classifier.pt'))
_ = classifier.to('cpu')
models = Translators()

while(True):
    try:
        records = consumer.poll(1.0)
        for topic_data, consumer_records in records.items():
            for consumer_record in consumer_records:
                key = str(consumer_record.key.decode('utf-8'))
                key = int(key)
                value = str(consumer_record.value.decode('utf-8'))
                standard_form,region = translate_response(key,value)
                print(region, standard_form)
                producer.send(region,standard_form)
    except:
        pass
    time.sleep(1)
        
consumer.close()


        
