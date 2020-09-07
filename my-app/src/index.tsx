import * as React from 'react';
import * as ReactDOM from 'react-dom';
// import App from './App';
import Hello from './components/StatefulHello';
import './index.css';
import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(
  // <App />,
  <Hello name="TypeScript" enthusiasmLevel={6} />,
  document.getElementById('root') as HTMLElement
);
registerServiceWorker();
