/*
 * @Author: your name
 * @Date: 2020-09-07 10:48:34
 * @LastEditTime: 2020-09-07 10:54:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /my-app/src/setupTests.ts
 */
import * as enzyme from 'enzyme';
import * as Adapter from 'enzyme-adapter-react-16';


enzyme.configure({ adapter: new Adapter() });