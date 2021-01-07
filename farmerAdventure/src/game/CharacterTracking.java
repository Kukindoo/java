package game;

import city.cs.engine.Body;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.WorldView;
import org.jbox2d.common.Vec2;

public class CharacterTracking implements StepListener{


        /** The view */
        private WorldView view;
        /** The body */
        private Body body;
    public CharacterTracking(WorldView view, Body body) {
        this.view = view;
        this.body = body;
    }


    @Override
    public void postStep(StepEvent e) {
        view.setCentre(new Vec2(body.getPosition().x,body.getPosition().y+7));
    }
        @Override
        public void preStep(StepEvent e) {
    }

    //
    //Setters
    //
    public void setBody(Body body) {
        this.body = body;
    }
}
