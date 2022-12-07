import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import SpeechRecognition, {
  useSpeechRecognition,
} from 'react-speech-recognition';
const useStyles = makeStyles(() => ({
  root: {
    '& button': {
      width: '32%',
      padding: '12px 9px',
      marginTop: '16px',
      borderRadius: '3px',
      cursor: 'pointer',
      marginLeft: '5px',
    },
  },
  mic: {
    width: '100%',
    textAlign: 'center',
    padding: '16px 0',
  },
  micON: {
    position: 'relative',
    marginLeft: '10px',
    background: '#27ae60',
    color: '#fff',
    padding: '4px 10px',
    borderRadius: '3px',
  },
  micOFF: {
    position: 'relative',
    marginLeft: '10px',
    background: 'red',
    color: '#fff',
    padding: '4px 10px',
    borderRadius: '3px',
  },
  micStart: {
    background: '#27ae60',
    color: '#fff',
  },
  micStop: {
    background: 'red',
    color: '#fff',
  },
  recordResult: {
    width: '29rem',
    minHeight: '150px',
    border: 'solid 1px #e5e5e5',
    marginTop: '5px',

    borderRadius: '3px',
  },
}));

const VoiceRecord = ({ resultTxt }) => {
  const classes = useStyles();

  const {
    transcript,
    listening,
    resetTranscript,
    browserSupportsSpeechRecognition,
  } = useSpeechRecognition();

  if (!browserSupportsSpeechRecognition) {
    return <span>Browser doesn't support speech recognition.</span>;
  }

  const startListening = () => {
    SpeechRecognition.startListening();
  };

  const stopListening = () => {
    SpeechRecognition.stopListening();
    resultTxt(transcript);
    // resultTxt('안녕');
  };

  return (
    <>
      <div className={classes.root}>
        <p className={classes.mic}>
          Microphone:
          {listening ? (
            <span className={classes.micON}>ON</span>
          ) : (
            <span className={classes.micOFF}>OFF</span>
          )}
        </p>

        <button className={classes.micReset} onClick={resetTranscript}>
          Reset
        </button>
        <button className={classes.micStart} onClick={startListening}>
          MIC ON
        </button>
        <button className={classes.micStop} onClick={stopListening}>
          MIC OFF
        </button>
        <div className={classes.recordResult}>
          <p>{transcript}</p>
        </div>
      </div>
    </>
  );
};

export default VoiceRecord;
