public class Body{
    /* parameter of Body */
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    /* Constructor for Body*/
    public Body(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b){
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    /* Calculate distance between Body this and Body b */
    public double calcDistance(Body b){
        double dx = b.xxPos - this.xxPos;
        double dy = b.yyPos - this.yyPos;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /* Calculate Exerted Force by Body b */
    public double calcForceExertedBy(Body b){
        double dist = this.calcDistance(b);
        double G = 6.67e-11;
        double force = G * this.mass * b.mass / (dist * dist);
        return force;
    }

    /* Calculate Exerted Force by Body b in X direction */
    public double calcForceExertedByX(Body b){
        double dx = b.xxPos - this.xxPos;
        double force = this.calcForceExertedBy(b);
        double dist = this.calcDistance(b);

        double force_x = force * dx / dist;
        return force_x;
    }

    /* Calculate Exerted Force by Body b in Y direction */
    public double calcForceExertedByY(Body b){
        double dy = b.yyPos - this.yyPos;
        double force = this.calcForceExertedBy(b);
        double dist = this.calcDistance(b);

        double force_y = force * dy / dist;
        return force_y;
    }

    /* Calculate Net X-direction Force by all Body containing in b_list */
    public double calcNetForceExertedByX(Body[] b_list){
        double net_force = 0;
        for (Body b: b_list){
            if (this.equals(b))
                continue;
            net_force += this.calcForceExertedByX(b);
        }
        return net_force;
    }

    /* Calculate Net Y-direction Force by all Body containing in b_list */
    public double calcNetForceExertedByY(Body[] b_list){
        double net_force = 0;
        for (Body b: b_list){
            if (this.equals(b))
                continue;
            net_force += this.calcForceExertedByY(b);
        }
        return net_force;
    }

    /* Position Update Method */
    public void update(double dt, double force_X, double force_Y){
        // calculate acceleration
        double ax = force_X / this.mass;
        double ay = force_Y / this.mass;

        // update velocity
        this.xxVel += (dt * ax);
        this.yyVel += (dt * ay);

        // update position
        this.xxPos += (dt * this.xxVel);
        this.yyPos += (dt * this.yyVel);
    }

    /* draw body API with StdDraw*/
    public void draw(){
        String imgfile = "images/" + this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, imgfile);
    }
}