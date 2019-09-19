from data_process import *
from extract_feats import *
import numpy as np

json_path=''
n_lstm_steps = 80
VIDEO_DIR = '/root/video_test/'
word_counts, unk_required = build_vocab(word_count_threshold=0)
word2id, id2word = word_to_ids(word_counts, unk_requried=unk_required)


# fetch features of train videos
#size of train batch can be 8.
def fetch_train_data(batch_size,model,tran):
    with open(json_path+'/root/train_data.json') as f:
        js=json.load(f)
    length=len(js['sentences'])
    for sentences in range(length)[::batch_size]:
        files=[]
        captions=[]
        if sentences+batch_size>length:
            break
        for i in range(sentences,sentences+batch_size):
            file=VIDEO_DIR+js['sentences'][i]['video_id']+'.avi'
            start_time=float(js['videos'][i]['start time'])
            end_time=float(js['videos'][i]['end time'])
            time_point=float(js['sentences'][i]['time_point'])
            tup=(file,start_time,end_time,time_point)
            files.append(tup)
            captions.append(js['sentences'][i]['caption'])
        feats=extract_feats(files,model,tran)
        captions, cap_mask = convert_caption(captions, word2id, n_lstm_steps)
        yield feats, captions, cap_mask
# vid_size = [batch_size, 80, 4096]     caption_size = [batch_size, 80]


# fetch features of val videos
def fetch_val_data(batch_size,model,tran):
    with open(json_path+'/root/test_data.json') as f:
        js=json.load(f)
    length=len(js['sentences'])
    for sentences in range(length)[::batch_size]:
        files=[]
        captions=[]
        if sentences+batch_size>length:
            break
        for i in range(sentences,sentences+batch_size):
            file=VIDEO_DIR+js['sentences'][i]['video_id']+'.avi'
            start_time=float(js['videos'][i]['start time'])
            end_time=float(js['videos'][i]['end time'])
            time_point=float(js['sentences'][i]['time_point'])
            tup=(file,start_time,end_time,time_point)
            files.append(tup)
            captions.append(js['sentences'][i]['caption'])
        feats=extract_feats(files,model,tran)
        captions, cap_mask = convert_caption(captions, word2id, n_lstm_steps)
        yield feats, captions, cap_mask
        break


# print captions
def print_in_english(captions):
    captions_english = [[id2word[word] for word in caption] for caption in captions]
    # captions_english = [[row[i] for row in captions_english] for i in range(len(captions_english[0]))]
    for cap in captions_english:
        if '<EOS>' in cap:
            cap = cap[0:cap.index('<EOS>')]
        print(' ' + ' '.join(cap))



