public class NBody{
    /* Read Radius of Universe */
    public static double readRadius(String fileName){
        In nbody_file = new In(fileName);
        int N = nbody_file.readInt();
        return nbody_file.readDouble();
    }

    /* Read bodies in to body array */
    public static Body[] readBodies(String fileName){
        In nbody_file = new In(fileName);
        int body_number = nbody_file.readInt();
        double radius = nbody_file.readDouble();

        Body[] body_list = new Body[body_number];
        for(int i =0; i<body_number; ++i){
            double xP = nbody_file.readDouble();
            double yP = nbody_file.readDouble();
            double xV = nbody_file.readDouble();
            double yV = nbody_file.readDouble();
            double m = nbody_file.readDouble();
            String img = nbody_file.readString();

            body_list[i] = new Body(xP, yP, xV, yV, m, img);
        }
        return body_list;
    }

    public static void main(String[] args) {
        // parse argument
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        // read body info and radius
        Body[] body_list = readBodies(filename);
        double radius = readRadius(filename);

        // set plotting parameter
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        
        // draw back ground
        String background = "images/starfield.jpg";
        StdDraw.picture(0, 0, background);

        // draw bodies
        for(Body b: body_list)
            b.draw();
        
        // create animation
        double t = 0;
        int N = body_list.length;
        while(t<T){
            double[] xForces = new double[N];
            double[] yForces = new double[N];

            // netForce for each body
            for (int i=0; i<N; ++i){
                xForces[i] = body_list[i].calcNetForceExertedByX(body_list);
                yForces[i] = body_list[i].calcNetForceExertedByY(body_list);
            }

            //update all body info
            for(int i=0; i<N; ++i){
                body_list[i].update(dt, xForces[i], yForces[i]);
            }

            // draw background image
            StdDraw.setScale(-radius, radius);
            StdDraw.picture(0, 0, background);
            
            // draw bodies
            for(Body b: body_list)
                b.draw();

            // show plot
            StdDraw.show();
            StdDraw.pause(10);

            // update time
            t += dt;
        }

        // pring simulation log
        StdOut.printf("%d\n", body_list.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < body_list.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  body_list[i].xxPos, body_list[i].yyPos, body_list[i].xxVel,
                  body_list[i].yyVel, body_list[i].mass, body_list[i].imgFileName);   
}
        // StdDraw.show();
		// StdDraw.pause(2000);
        
    }
}