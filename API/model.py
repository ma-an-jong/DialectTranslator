import torch
from torch import nn
from kobart import get_kobart_tokenizer
from transformers.models.bart import BartForConditionalGeneration
import torch.nn.functional as F
from kss import split_sentences

class Translators():
    def __init__(self):
        self.__models = [
                BartForConditionalGeneration.from_pretrained('models/강원도 모델').to("cuda"),
                 BartForConditionalGeneration.from_pretrained('models/경상도 모델').to("cuda"),
                 BartForConditionalGeneration.from_pretrained('models/전라도 모델').to("cuda"),
                 BartForConditionalGeneration.from_pretrained('models/제주도 모델').to("cuda"),
                 BartForConditionalGeneration.from_pretrained('models/충청도 모델').to("cuda")
                ]
        self.tokenizer = get_kobart_tokenizer()

        self.Gangwon_label = 0
        self.Gyeongsang_label = 1
        self.Jeolla_label = 2
        self.Jeju_label = 3
        self.Chungcheong_label = 4
        
        self.dict = {
                        0:"강원도",
                        1:"경상도",
                        2:"전라도",
                        3:"제주도",
                        4:"충청도",
                    }
    def valid(self,sentence):
        return len(self.tokenizer.tokenize(sentence)) > 128
    
    def get_response(self,content,index):
        input_ids = self.tokenizer.encode(content)
        input_ids = torch.tensor(input_ids)
        input_ids = input_ids.unsqueeze(0).to("cuda")
        output = self.__models[index].generate(input_ids, eos_token_id=1, max_length=512, num_beams=5)
        output = self.tokenizer.decode(output[0], skip_special_tokens=True)
        return output
    
    def get_many_response(self,content,index):
        split_content = split_sentences(content)
        outputs = []
        for piece in split_content:
            output = self.get_response(piece,index)
            outputs.append(output)
        
        return str(outputs)