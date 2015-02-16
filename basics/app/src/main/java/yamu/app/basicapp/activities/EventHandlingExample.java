package yamu.app.basicapp.activities;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yamu.app.basicapp.R;

public class EventHandlingExample extends ActionBarActivity
{

    private Button btnPressMe;
    private TextView txtViewDisplayBtnResponse;
    private RelativeLayout relLayoutEventHandling;
    private TextView txtViewGestures;
    private TextView txtViewTouchEventUpDown;
    private TextView txtViewTouchEventMove;
    private GestureEventListener gestureListener;
    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_handling_example);
        setupViews();

        this.gestureListener = new GestureEventListener();
        this.gestureDetector = new GestureDetectorCompat(this, this.gestureListener);
        this.gestureDetector.setOnDoubleTapListener(this.gestureListener);
    }


    private void setupViews() {
        this.btnPressMe = (Button) findViewById(R.id.btnPressMe);
        this.txtViewDisplayBtnResponse = (TextView) findViewById(R.id.txtViewDisplayBtnResponse);
        this.txtViewDisplayBtnResponse.setText(getText(R.string.hello_world));
        this.relLayoutEventHandling = (RelativeLayout) findViewById(R.id.relLayoutEventHandling);
        this.txtViewGestures = (TextView) findViewById(R.id.txtViewGestures);
        this.txtViewTouchEventUpDown = (TextView) findViewById(R.id.txtViewTouchEventUpDown);
        this.txtViewTouchEventMove = (TextView) findViewById(R.id.txtViewTouchEventMove);

        this.btnPressMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventHandlingExample.this.txtViewDisplayBtnResponse
                        .setText("Someone clicked!");
            }
        });

        this.btnPressMe.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EventHandlingExample.this.txtViewDisplayBtnResponse
                        .setText("Someone clicked for a long time!");
                return true;
            }
        });

        this.relLayoutEventHandling.setOnTouchListener(new View.OnTouchListener() {
            private final static int INVALID_POINTER_ID = -1;
            private int activePointerId = INVALID_POINTER_ID;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                StringBuilder upDownBuilder = new StringBuilder();
                StringBuilder moveBuilder   = new StringBuilder();

                EventHandlingExample.this.gestureDetector.onTouchEvent(event);

                final int action = event.getAction();

                int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                int pointerId = event.getPointerId(pointerIndex);

                switch (action & MotionEvent.ACTION_MASK)
                {
                    case MotionEvent.ACTION_DOWN: {

                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        String location = "(" + x + "," + y + ")";
                        this.activePointerId = event.getPointerId(0);
                        upDownBuilder.append("ACTION_DOWN\n" + location + "\npointer_id=" + pointerId);
                        break;
                    }

                    case MotionEvent.ACTION_POINTER_DOWN: {
                        int x = (int) event.getX(pointerIndex);
                        int y = (int) event.getY(pointerIndex);
                        String location = "(" + x + "," + y + ")";
                        upDownBuilder.append("ACTION_POINTER_DOWN\n" + location + "\npointer_id=" + pointerId);
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        moveBuilder.append("Pointer ID=" + pointerId + "\n");

                        for (int i = 0; i < event.getPointerCount(); ++i) {
                            int id = event.getPointerId(i);
                            int x = (int) event.getX(i);
                            int y = (int) event.getY(i);
                            String location = "(" + x + "," + y + ")";
                            moveBuilder.append("ID:" + id + ":" + location + "\n");
                        }
                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        int x = (int) event.getX(pointerIndex);
                        int y = (int) event.getY(pointerIndex);
                        String location = "(" + x + "," + y + ")";
                        upDownBuilder.append("ACTION_UP\n" + location + "\npointer_id=" + pointerId);
                        this.activePointerId = INVALID_POINTER_ID;
                        break;
                    }

                    case MotionEvent.ACTION_POINTER_UP: {
                        if (pointerId == activePointerId) {
                            // Switch Active Pointer
                            if (pointerIndex == 0) {
                                pointerIndex = 1;
                            } else {
                                pointerIndex = 0;
                            }
                            this.activePointerId = event.getPointerId(pointerIndex);
                        }
                        int x = (int) event.getX(pointerIndex);
                        int y = (int) event.getY(pointerIndex);
                        String location = "(" + x + "," + y + ")";
                        upDownBuilder.append("ACTION_POINTER_UP\n" + location + "\npointer_id=" + pointerId);
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:
                        this.activePointerId = INVALID_POINTER_ID;
                        break;
                }


                if (upDownBuilder.length() > 0) {
                    if (this.activePointerId == INVALID_POINTER_ID) {
                        upDownBuilder.append("\nactive_id=NONE");
                    } else {
                        upDownBuilder.append("\nactive_id=" + activePointerId);
                    }
                    EventHandlingExample.this.txtViewTouchEventUpDown.setText(upDownBuilder.toString());
                }
                String move = moveBuilder.toString();
                EventHandlingExample.this.txtViewTouchEventMove.setText(move);

                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_handling_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GestureEventListener implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener
    {

        @Override
        public boolean onDown(MotionEvent e) {
            EventHandlingExample.this.txtViewGestures.setText("OnDown!");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            EventHandlingExample.this.txtViewGestures.setText("OnShowPress!");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            EventHandlingExample.this.txtViewGestures.setText("OnSingleTapUp!");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            EventHandlingExample.this.txtViewGestures.setText("OnScrollTapUp!");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            EventHandlingExample.this.txtViewGestures.setText("OnLongPress!");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            EventHandlingExample.this.txtViewGestures.setText("OnFling!");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            EventHandlingExample.this.txtViewGestures.setText("OnSingleTapConfirmed!");
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            EventHandlingExample.this.txtViewGestures.setText("OnDoubleTap!");
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            EventHandlingExample.this.txtViewGestures.setText("OnDoubleTapEvent!");
            return true;
        }
    }
}
