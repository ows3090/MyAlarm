package ows.boostcourse.myalarm.Interface;

import ows.boostcourse.myalarm.Component.AlarmAdapter;

/**
 * MVP architecture에서 MainView를 사용하기 위한 Interface
 */
public interface MainView {
    void onInitView(AlarmAdapter adapter);
}
