from fastapi import FastAPI
from typing import Union
import torch
import torch.nn.functional as F
from pydantic import BaseModel
from fastapi.responses import RedirectResponse
from model import Translators
from classifier import DialectClassifier
from kss import split_sentences

class Item(BaseModel):
    dialect : str
    label : Union[int, None] = None

classifier = DialectClassifier()
classifier.load_state_dict(torch.load('models/classifier.pt'))
_ = classifier.to('cuda')
models = Translators()

app = FastAPI()


@app.get("/")
async def root():
    return {"message": "동네사람들 API"}

@app.post("/translate")
async def translate_response(item:Item):
    key = item.dialect
    print(item.dialect,item.label) 
    try:
        if item.label == None:
            label = classifier.classification(item.dialect)
        else:
            label = item.label
        
        if(models.valid(item.dialect)):
            value = models.get_many_response(content = item.dialect,index = label)
        else:
            value =models.get_response(content = item.dialect,index = label)
            
        key =  models.dict[label]
        
        return {"standard_form" : value,"region" : key}
    except IndexError:
        return {"except" : "unvalid labeling"}
        
