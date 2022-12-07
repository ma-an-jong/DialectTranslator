import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Container, Typography } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    height: '100px',
    borderBottom: 'solid 1px #e5e5e5',
  },
  text: {
    fontWeight: 'bold',
    color: '#27AE60',
    lineHeight: '100px',
  },
}));

const Header = () => {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <Container maxWidth="lg">
        <Typography className={classes.text} variant="h3" gutterBottom>
          동네 사람들
        </Typography>
      </Container>
    </div>
  );
};

export default Header;
