import './App.css';
import '@fontsource/roboto';
import Header from './components/Header';
import Translate from './pages/Translate';

const App = () => {
  return (
    <>
      {/* Header  */}
      <Header />

      {/* content */}
      <Translate />
    </>
  );
};

export default App;
