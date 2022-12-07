import React, { useState, useCallback } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { BiUpload, BiMicrophone } from 'react-icons/bi';
import axios from 'axios';
import FileDrop from './FileDrop';
import VoiceRecord from './VoiceRecord';

import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import useEnhancedEffect from '@mui/material/utils/useEnhancedEffect';

const useStyles = makeStyles((theme) => ({
  icon: {
    float: 'right',
    marginRight: '15px',
    marginTop: '10px',
    cursor: 'pointer',
  },

  TextField: {
    width: '96%',
    height: '280px',
    paddingTop: '15px',
    paddingLeft: '15px',
    fontSize: '1.7em',
    resize: 'none',
    border: 'solid 1px #c8c8c8',
    borderRadius: '3px',
    marginTop: '3px',
    transition: 'all .3s',
    '&:focus': {
      outline: 'none',
      borderColor: '#27AE60',
    },
  },
  btnArea: {
    width: '100%',
    height: 'auto',
  },
  button: {
    float: 'right',
    background: '#27AE60',
    color: '#fff',
    padding: '12px 55px',
    marginTop: '5px',
    marginRight: '7px',
    borderRadius: '3px',
  },
}));

const DialectForm = ({ onInsert }) => {
  const [fileOpen, setFileOpen] = React.useState(false);
  const [voiceOpen, setVoiceOpen] = React.useState(false);
  const [voiceSTT, setVoiceSTT] = React.useState('');

  const classes = useStyles();
  const [value, setValue] = useState({
    region: '',
    txt: '',
  });

  const voiceDialogOpen = () => {
    setVoiceOpen(true);
  };

  const voiceClose = () => {
    setVoiceOpen(false);
  };

  const fileDialogOpen = () => {
    setFileOpen(true);
  };

  const fileClose = () => {
    setFileOpen(false);
  };

  const onChangeRegion = useCallback(
      (e) => {
        setValue({
          region: e.target.value,
          txt: value.txt,
        });
      },
      [value],
  );

  const onChangeTxt = useCallback(
      (e) => {
        setValue({
          region: value.region,
          txt: e.target.value,
        });
      },
      [value],
  );

  function mapping(region) {
    if (region === '강원도') {
      return 0;
    } else if (region === '경상도') {
      return 1;
    } else if (region === '전라도') {
      return 2;
    } else if (region === '제주도') {
      return 3;
    } else if (region === '충청도') {
      return 4;
    } else return undefined;
  }

  const handleKeyPress = (e) => {
    if (e.key === 'Enter' && e.shiftKey) {
      // [shift] + [Enter] 치면 걍 리턴
      return;
    } else if (e.key === 'Enter') {
      // [Enter] 치면 메시지 보내기
      e.preventDefault();
      setValue({
        region: value.region,
        txt: e.target.value,
      });
      // console.log(value.region + ' + ' + e.target.value);
      onSubmit();
    }
  };

  const onSubmit = useCallback(
      (e) => {
        const url = 'https://kle445.iptime.org/api/translate';
        const header = { 'Content-type': 'application/json' };
        const crossOriginIsolated = { withCredentials: true };
        const data = {
          dialect: value.txt,
          label: mapping(value.region),
        };
        axios
            .post(url, data, header, crossOriginIsolated)
            .then((response) => {
              console.log(crossOriginIsolated);
              onInsert(response.data.region, response.data.standard_form);
            })
            .catch((err) => console.log(`Error Occured : ${err}`));

        e.preventDefault();
      },
      [onInsert, value],
  );

  const voiceSubmit = () => {
    setValue({
      region: '',
      txt: voiceSTT,
    });

    setVoiceOpen(false);
  };

  const resultTxt = (text) => {
    // console.log(text);
    setVoiceSTT(text);
  };

  return (
      <>
        <form onSubmit={onSubmit}>
          <select
              value={value.region}
              className="selectRegion"
              onChange={onChangeRegion}
              label="지역선택"
          >
            <option value="자동감지">자동 감지</option>
            <option value="강원도">강원도</option>
            <option value="전라도">전라도</option>
            <option value="경상도">경상도</option>
            <option value="제주도">제주도</option>
            <option value="충청도">충청도</option>
          </select>
          <textarea
              className={classes.TextField}
              placeholder="사투리를 입력하세요"
              value={value.txt}
              onChange={onChangeTxt}
              onKeyDown={handleKeyPress}
          />
          <div className={classes.btnArea}>
            <button className={classes.button} type="submit">
              번역하기
            </button>
            <BiUpload
                size={32}
                onClick={fileDialogOpen}
                className={classes.icon}
            />
            <BiMicrophone
                size={32}
                onClick={voiceDialogOpen}
                className={classes.icon}
            />
          </div>
        </form>

        {/* voice record */}
        <Dialog open={voiceOpen} onClose={voiceClose}>
          <DialogTitle>파일 번역</DialogTitle>
          <DialogContent>
            <DialogContentText>직접 말해서 번역하세요.</DialogContentText>
            <VoiceRecord resultTxt={resultTxt} />
          </DialogContent>
          <DialogActions>
            <Button onClick={voiceClose}>닫기</Button>
            <Button onClick={voiceSubmit}>입력</Button>
          </DialogActions>
        </Dialog>

        {/* file read */}
        <Dialog open={fileOpen} onClose={fileClose}>
          <DialogTitle>파일 번역</DialogTitle>
          <DialogContent>
            <DialogContentText>
              음성파일 또는 영상파일을 업로드 해주세요.
            </DialogContentText>
            <FileDrop />
          </DialogContent>
          <DialogActions>
            <Button onClick={fileClose}>닫기</Button>
            <Button onClick={fileClose}>번역</Button>
          </DialogActions>
        </Dialog>
      </>
  );
};

export default DialectForm;
