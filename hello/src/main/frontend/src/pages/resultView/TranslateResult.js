import React, { useState } from 'react';
import { Typography } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import axios from 'axios';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  regionBox: {
    width: '95%',
    padding: '14px 14px',
    borderBottom: 'solid 1px #e5e5e5',
  },
  regionTxt: {
    color: '#B0B0B0',
  },
  resultTxt: {
    width: '95%',
    padding: '14px  14px',
  },
  userIdea: {
    width: '100%',
    textAlign: 'right',
    fontSize: '1.0em',
    '& p': {
      paddingTop: '10px',
      color: '#333',
      cursor: 'pointer',
    },
    userIcon: {
      paddingTop: '20px',
    },
    suggestionTxt: {
      width: '90%',
      margin: '0 auto',
    },
  },
}));

export const TranslateResult = ({ resultData }) => {
  const [suggestionsOpen, setSuggestionsOpen] = React.useState(false);

  const classes = useStyles();

  const suggestionsDialogOpen = () => {
    setSuggestionsOpen(true);
  };

  const suggestionsClose = () => {
    setSuggestionsOpen(false);
  };

  const submitSuggestion = (e) => {

    const { suggestion } = e.target;
    const url = 'https://kle445.iptime.org/api/report';
    const header = { 'Content-type': 'application/json' };
    const crossOriginIsolated = { withCredentials: true };
    axios
        .post(url,{"content" : suggestion.value}, header, crossOriginIsolated)
        .then((response) => {
          console.log(response.data);
        })
        .catch((err) => console.log(`Error Occured : ${err}`));
    alert("의견 남겨주셔서 감사합니다")
    e.preventDefault();

  };

  return (
    <>
      <div className="resultBox">
        <div className={classes.regionBox}>
          <Typography className={classes.regionTxt}>
            선택된 지역 : {resultData && resultData.region}
          </Typography>
        </div>
        <div className={classes.resultTxt}>
          <Typography>{resultData && resultData.value}</Typography>
        </div>
      </div>
      <div className={classes.userIdea}>
        <p onClick={suggestionsDialogOpen}>사용자 의견</p>
      </div>

      

      {/* file read */}
      <Dialog open={suggestionsOpen} onClose={suggestionsClose}>
        <form onSubmit={(e) => submitSuggestion(e)}>
          <DialogTitle>사용자 의견</DialogTitle>
          <DialogContent>
            <DialogContentText>
              번역이 잘못 되었나요? 의견을 남겨주세요!
            </DialogContentText>
            <div className="suggestionForm">
              <textarea placeholder="의견을 작성해주세요" name="suggestion" />
            </div>
          </DialogContent>

          <DialogActions>
            <Button onClick={suggestionsClose}>닫기</Button>
            <Button type="submit" onClick={suggestionsClose}>
              제출
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    </>
  );
};
