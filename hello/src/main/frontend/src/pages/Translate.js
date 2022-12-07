import React, { useEffect, useState, useCallback } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Container, Typography, Grid, Paper } from '@material-ui/core';
import DialectForm from './translate/DialectForm';
import { TranslateResult } from './resultView/TranslateResult';
import axios from 'axios';
const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  Paper: {
    marginTop: '30px',
  },
}));

const Translate = () => {
  const classes = useStyles();

  const [translateData, setTranslateData] = useState({
    region: '',
    value: '',
  });

  const onInsert = useCallback(
    (region, value) => {
      setTranslateData({
        region: region,
        value: value,
      });
    },
    [translateData],
  );

  return (
    <div className={classes.root}>
      <Container>
        <Grid container spacing={6}>
          <Grid className={classes.gridItem} item xs={6}>
            <Paper className={classes.Paper} elevation={0}>
              {/* title : 사투리 */}
              <Typography variant="h5" gutterBottom>
                사투리
              </Typography>
              {/* DialectForm */}
              <DialectForm onInsert={onInsert} />
            </Paper>
          </Grid>
          <Grid className={classes.gridItem} item xs={6}>
            <Paper className={classes.Paper} elevation={0}>
              {/* title : 표준어 */}
              <Typography variant="h5" gutterBottom>
                표준어
              </Typography>
              {/* result display */}
              <TranslateResult resultData={translateData} />
            </Paper>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
};

export default Translate;
