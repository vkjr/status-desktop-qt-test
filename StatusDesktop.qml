import QtQuick 2.4
import React 0.1 as React

Rectangle {
  id: root
  width: 800; height: 600;

  React.RootView {
    anchors.fill: parent

    moduleName: "StatusIm"
    codeLocation: "http://localhost:8081/index.bundle?platform=desktop&dev=true"
  }
}
