import torch
from torch import nn
from transformers import AutoModel,AutoTokenizer
from kobert_tokenizer import KoBERTTokenizer
import torch.nn.functional as F

class DialectClassifier(nn.Module):
    def __init__(self, dropout=0.5):
        super(DialectClassifier, self).__init__()
        self.tokenizer = KoBERTTokenizer.from_pretrained("skt/kobert-base-v1")
        self.bert = AutoModel.from_pretrained("skt/kobert-base-v1")
        self.dropout1 = nn.Dropout(dropout)
        self.linear1 = nn.Linear(768, 1024)
        self.dropout2 = nn.Dropout(dropout)
        self.linear2 = nn.Linear(1024,5)
        
    def forward(self, input_id, mask):
        last_hidden_state,_ = self.bert(input_ids= input_id, attention_mask=mask,return_dict=False)
        dropout_output = self.dropout1(last_hidden_state)
        linear_output = self.linear1(dropout_output)
        dropout_output1 = self.dropout2(linear_output)
        linear_output1 = self.linear2(dropout_output1)
        return linear_output1[:,0,:]
    
    def classification(self,inp):
        model_input = self.tokenizer(inp,padding='max_length', max_length = 512, truncation=True,return_tensors="pt")
        mask = model_input['attention_mask'].to("cuda")
        input_ids = model_input['input_ids'].to("cuda")
        output = self.forward(input_ids,mask)
        label = torch.argmax(F.softmax(output, dim=1), 1).item()
        return label