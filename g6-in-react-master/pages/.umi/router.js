import React from 'react';
import {
  Router as DefaultRouter,
  Route,
  Switch,
  StaticRouter,
} from 'react-router-dom';
import dynamic from 'umi/dynamic';
import renderRoutes from 'umi/lib/renderRoutes';
import history from '@@/history';

const Router = DefaultRouter;

const routes = [
  {
    path: '/component/contextMenu',
    exact: true,
    component: require('../component/contextMenu.js').default,
  },
  {
    path: '/component/edgTooltip',
    exact: true,
    component: require('../component/edgTooltip.js').default,
  },
  {
    path: '/component',
    exact: true,
    component: require('../component/index.js').default,
  },
  {
    path: '/component/nodeTooltip',
    exact: true,
    component: require('../component/nodeTooltip.js').default,
  },
  {
    path: '/data',
    exact: true,
    component: require('../data.js').default,
  },
  {
    path: '/',
    exact: true,
    component: require('../index.js').default,
  },
  {
    path: '/registerShape',
    exact: true,
    component: require('../registerShape.js').default,
  },
  {
    path: '/tree',
    exact: true,
    component: require('../tree/index.js').default,
  },
  {
    component: () =>
      React.createElement(
        require('/Users/luyan1/learning/g6-in-react-master/node_modules/umi-build-dev/lib/plugins/404/NotFound.js')
          .default,
        { pagesPath: 'pages', hasRoutesInConfig: false },
      ),
  },
];
window.g_routes = routes;
const plugins = require('umi/_runtimePlugin');
plugins.applyForEach('patchRoutes', { initialValue: routes });

export { routes };

export default class RouterWrapper extends React.Component {
  unListen() {}

  constructor(props) {
    super(props);

    // route change handler
    function routeChangeHandler(location, action) {
      plugins.applyForEach('onRouteChange', {
        initialValue: {
          routes,
          location,
          action,
        },
      });
    }
    this.unListen = history.listen(routeChangeHandler);
    // dva 中 history.listen 会初始执行一次
    // 这里排除掉 dva 的场景，可以避免 onRouteChange 在启用 dva 后的初始加载时被多执行一次
    const isDva =
      history.listen
        .toString()
        .indexOf('callback(history.location, history.action)') > -1;
    if (!isDva) {
      routeChangeHandler(history.location);
    }
  }

  componentWillUnmount() {
    this.unListen();
  }

  render() {
    const props = this.props || {};
    return <Router history={history}>{renderRoutes(routes, props)}</Router>;
  }
}
