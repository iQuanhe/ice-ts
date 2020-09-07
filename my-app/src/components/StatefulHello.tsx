// src/components/StatefulHello.tsx

import * as React from "react";
import './css/Hello.css';

export interface Props {
  name: string;
  enthusiasmLevel?: number;
}

interface State {
  current: number;
}

class Hello extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = { current: props.enthusiasmLevel || 1 };
  }

  updateEnthusiasm(current: number) {
    this.setState({ current });
  }

  onIncrement = () => this.updateEnthusiasm(this.state.current + 1);
  onDecrement = () => this.updateEnthusiasm(this.state.current - 1);

  render() {
    const { name } = this.props;

    if (this.state.current <= 0) {
      throw new Error('You could be a little more enthusiastic. :D');
    }

    return (
      <div className="hello">
        <div className="greeting">
          Hello {name + getExclamationMarks(this.state.current)}
        </div>
        <button onClick={this.onDecrement}>-</button>
        <button onClick={this.onIncrement}>+</button>
      </div>
    );
  }
  
}

export default Hello;

function getExclamationMarks(numChars: number) {
  return Array(numChars + 1).join('ok!');
}