import { makeStyles } from '@material-ui/core/styles';

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

export default useStyles;
