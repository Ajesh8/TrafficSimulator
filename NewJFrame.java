/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ajesh Kumar Yadav
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Polygon;
class Car //This class contains the basic information of the car and used to create cars
{
    int curr_x,curr_y;//Defines current position of car
    int speed;
    int h=0,v=0;//if car is in horizontal or vertical motion
    
    int turn=0;//1 if the car will turn left, 2 if car will turn right, 0 if it doesn't turn
    int dest_sel;
    String source,destination;//Source and destination of car
    int spawn=0; //To check if the car has spawned on the map or not
    int colour=0; //To provide a color to the car
    int moving=0;//Not used till now, would be used when providing stop mechanism
    int path;//Determines what lane the car has taken
    void set()  //Sets the route taken by car according to lane of car
    {
    switch (path) {
        case 0:    //Left most lane going east
            curr_x=-100;
            curr_y=520;
            source="E";
            h=1;v=0;
            break;
        
        case 1:     //Right lane while going east
            curr_x=-100;
            curr_y=580;
            source="E";
            h=1;v=0;
            break;
        case 2:   //Right lane while going west
            curr_x=1920;
            curr_y=660;
            source="W";
            h=1;v=0;
            break;
        case 3:   //Left most lane going west
            curr_x=1920;
            curr_y=730;
            source="W";
            h=1;v=0;
            break;
        case 4:    //North to South
            curr_x=995;
            curr_y=-100;
            source="N";
            h=0;v=1;
            break;
    }
    if((source=="E" && destination=="W") || (source=="W" && destination=="E"))//Car won't take a turn if it going from East to West or Vice versa
        turn=0;
    if((source=="E" && destination=="N") || (source=="N" && destination=="W"))//Car will take a left turn
        turn=1;
    if((source=="W" && destination=="N") || (source=="N" && destination=="E"))//Car will take a right turn
        turn=2;
    }
    
    
    
}

public class NewJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    ImageIcon iconLogo = new ImageIcon("Images/Label.png");//For the welcome frame
    public NewJFrame() {
        initComponents();
    }
class Driver extends Thread //This is the main class with the main frame
{   int p[][]=new int[220][120]; //Grid used to describe the map
    
    Car[] C=new Car[50];//Creating Instances of cars
    int traffic_light=0;//Variable for switching traffic light
    int traffic_interval=0;//Multiplier for finding out time to switch the traffic light
    int spawn_path=0;       //The variable used to calculate lane used by car
    int spawn_area[]=new int[5];// Array to find if the spawn area of a lane is occupied by a car or not
    
    Random randnum=new Random(System.currentTimeMillis());// To generate random numbers for various purposes
    int car_no=0;        //This number will determine how many cars would show up at max on the map
    int spawn_interval=0; //Determines the time interval between new car spawns
    int status=0;           //If the simulation has to stop or not
    JButton b,t1,t2,t3,c1,c2,c3,s1,s2,s3,tl,ct; //Jbutton declarations
    JLabel t,c,s,cno,ts,cs,ss;                  //JLabel declarations
    int traff_mul=1000,spawn_mul=50,cars_allow=20; //Initializing traffic and spawn multipliers after which an action takes place
    double spawn_sec=((double)(spawn_mul*5))/1000; //To convert spawn multiplier into seconds for display purpose
    
    
    JFrame j;
    Driver() //Constructor for Driver class
    { for(int i=0;i<50;i++)
        C[i]=new Car();     //Initializing all cars
    for(int i=0;i<220;i++)  //Setting all co-ordinates on the map as unoccupied
    {for(int j=0;j<120;j++) //Array starts at index 0 in both x-y plane, but co-ordinates range from x=-100 to 1920 and y=-100 to 1080
    {                       //Each index contains a square of 10 pixels wide
        p[i][j]=0;          // To calculate the index used by a particular point,add 100 to both x and y and divide by 10
    }
       
    }
    spawn_sec=spawn_sec/10;
     status=1;
    b=new JButton();                                                //Initializing buttons and labels for display
    t1=new JButton();t2=new JButton();t3=new JButton();
    c1=new JButton();c2=new JButton();c3=new JButton();
    s1=new JButton();s2=new JButton();s3=new JButton();
    tl=new JButton();
    ct=new JButton();
    t=new JLabel();
    c=new JLabel();
    s=new JLabel();
    cno=new JLabel();
    ts=new JLabel();
    cs=new JLabel();
    ss=new JLabel();
    j=new JFrame(); //Frame basic characterstics
    j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    j.setLayout(new java.awt.BorderLayout());           //j is the frame which holds the panel
    j.setSize(1920,1080);
    MyPanel M=new MyPanel();                           //Panel which is used to paint the simulation
    M.setLayout(null);
    b.setBounds(1400,50,200,50);
    t1.setBounds(1300,120,100,50);
    t2.setBounds(1450,120,100,50);
    t3.setBounds(1600,120,100,50);
    c1.setBounds(1300,190,100,50);
    c2.setBounds(1450,190,100,50);
    c3.setBounds(1600,190,100,50);
    s1.setBounds(1300,260,100,50);
    s2.setBounds(1450,260,100,50);
    s3.setBounds(1600,260,100,50);
    tl.setBounds(400,190,200,50);
    ct.setBounds(200,190,150,50);
    t.setBounds(1100,120,200,50);
    c.setBounds(1130,190,200,50);
    s.setBounds(1120,260,200,50);
    cno.setBounds(400,250,200,50);
    ts.setBounds(1750,120,100,50);
    cs.setBounds(1750,190,100,50);
    ss.setBounds(1750,260,100,50);
    cno.setFont(new Font("Serif",Font.PLAIN,20));
    ts.setFont(new Font("Serif",Font.PLAIN,20));
    cs.setFont(new Font("Serif",Font.PLAIN,20));
    ss.setFont(new Font("Serif",Font.PLAIN,20));
    s.setFont(new Font("Serif",Font.PLAIN,20));
    c.setFont(new Font("Serif",Font.PLAIN,20));
    t.setFont(new Font("Serif", Font.PLAIN, 20));
    t.setText("Traffic Light Interval");
    c.setText("Cars on Map");
    s.setText("Spawn Interval");
    cno.setText("Total Cars:"+car_no);
    ts.setText(Integer.toString((traff_mul*5)/1000));
    cs.setText(Integer.toString(cars_allow));
    ss.setText(Double.toString(spawn_sec));
    b.setText("Stop Simulation");
    t1.setText("5 Seconds");
    t2.setText("10 Seconds");
    t3.setText("15 Seconds");
    c1.setText("20 Cars");
    c2.setText("30 Cars");
    c3.setText("40 Cars");
    s1.setText("0.25 Second");
    s2.setText("0.5 Second");
    s3.setText("1 Second");
    
    tl.setText("Toggle Traffic Lights");
    ct.setText("Clear Traffic");
     
    M.add(t1);M.add(t2);M.add(t3);
    M.add(c1);M.add(c2);M.add(c3);
    M.add(s1);M.add(s2);M.add(s3);
    M.add(b);
    M.add(tl);
    M.add(ct);
    M.add(t);
    M.add(c);
    M.add(s);
    M.add(cno);
    M.add(ts);
    M.add(cs);
    M.add(ss);
    j.add(M,BorderLayout.CENTER);               //The following block of codes are just for adding functinality to the buttons
    ct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ctActionPerformed(evt);
            }
     
     });
    b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bActionPerformed(evt);
            }
     
     });
    t1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t1ActionPerformed(evt);
            }
     
     });
    t2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t2ActionPerformed(evt);
            }
     
     });
    t3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t3ActionPerformed(evt);
            }
     
     });
    c1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c1ActionPerformed(evt);
            }
     
     });
    c2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c2ActionPerformed(evt);
            }
     
     });
    c3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c3ActionPerformed(evt);
            }
     
     });
    s1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s1ActionPerformed(evt);
            }
     
     });
    s2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s2ActionPerformed(evt);
            }
     
     });
    s3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s3ActionPerformed(evt);
            }
     
     });
    tl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tlActionPerformed(evt);
            }
     
     });
    

    
}   private void ctActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        for(int i=0;i<50;i++)
            C[i].spawn=0;
        for(int i=0;i<220;i++)
    {for(int j=0;j<120;j++)
    {
        p[i][j]=0;
    }
       
    }
        car_no=0;
       
    }
    
    private void bActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        this.j.dispose();
        status=0;
       
       
    }
    private void t1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        traff_mul=1000;
       
       
    }
     private void t2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        traff_mul=2000;
       
       
    }
      private void t3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        traff_mul=3000;
       
       
    }
       private void c1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        cars_allow=20;
       
       
    }
        private void c2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        cars_allow=30;
       
       
    }
         private void c3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        cars_allow=40;
       
       
    }
          private void s1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        spawn_mul=50;
       
       
    }
           private void s2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        spawn_mul=100;
       
       
    }
            private void s3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        
       spawn_mul=200;
       
    }
             private void tlActionPerformed(java.awt.event.ActionEvent evt) { //Switching the traffic lights manually                                        
        // TODO add your handling code here:
                if(traffic_light==0)
                    traffic_light=1;
                else if(traffic_light==1)
                    traffic_light=2;
                else
                    traffic_light=0;
       
       
    }

    
    @Override
    public void run() //This thread calculates the position of cars at every instant
    {
        try
        {
        
        while(status==1)//Traffic lights controller
            {  if(traffic_interval>traff_mul) //Condition to flip the traffic light
            {
                traffic_interval=0;
                if(traffic_light==0)        //If traffic light is zero, only east to west traffic is allowed 
                    traffic_light=1;        //If traffic light is 1, only west to east traffic is allowed
                else if(traffic_light==1)   //If traffic light is 2, only north to south traffic is allowed
                    traffic_light=2;
                else
                    traffic_light=0;
            }
                if(spawn_interval>=spawn_mul && car_no<cars_allow) //This function spawns a new car after a specific time interval
                    { spawn_interval=0;
                    for(int k=0;k<5;k++)
                        spawn_area[k]=0;
                    
                      spawn_path=randnum.nextInt(5); //Selects the lane chosen by car
                        
                        for(int i=0;i<50;i++)// Checking if there is any car in the spawn area
                        {
                            if(C[i].spawn==1 && C[i].path==spawn_path) //This function checks all cars and sees if there is any car int the spawn area of the selected lane
                            {
                                if(spawn_path==0 || spawn_path==1) //If selected lane is east to west ones
                                {
                                    if(C[i].curr_x<20)
                                    {   
                                        spawn_area[spawn_path]=1;
                                        break;
                                    }
                                    else
                                        spawn_area[spawn_path]=0;
                                }
                                else if(spawn_path==2 || spawn_path==3) //If selected lane is west to east
                                {
                                    if(C[i].curr_x>1800)
                                    {   
                                        spawn_area[spawn_path]=1;
                                        break;
                                    }
                                    else
                                        spawn_area[spawn_path]=0;
                                        
                                }
                                else if(spawn_path==4 && C[i].path==4) //If selected lane is north to south
                                {
                                    if(C[i].curr_y<20)
                                    {
                                        spawn_area[spawn_path]=1;
                                        break;
                                    }
                                    else
                                        spawn_area[spawn_path]=0;
                                }
                            } 
                        }
                        for(int i=0;i<50;i++) //This function finds the car in the car array which hasn't spawn yet
                        {
                            if(C[i].spawn==0) //If car hasn't spawn, then enter
                        {    
                        
                        if(spawn_area[spawn_path]==0) //If spawn_area is zero, it means the car can spawn on the selected lane, otherwise not
                        {
                        C[i].dest_sel=randnum.nextInt(2); //Destination selecter
                        if(spawn_path==0 || spawn_path==1)//If source is East, destination can be West or North
                        {   if(C[i].dest_sel==0)
                                C[i].destination="W";
                            else
                                C[i].destination="N";
                        }
                        else if(spawn_path==2 || spawn_path==3)//If source is West, destination can be East or North
                        {
                            if(C[i].dest_sel==0)
                                C[i].destination="E";
                            else
                                C[i].destination="N";
                        }
                        else                                    //If source is North, destination can be West or East
                        {   if(C[i].dest_sel==0)
                                C[i].destination="E";
                            else
                                C[i].destination="W";
                        }
                            
                        ++car_no;                               //Increment car no
                        C[i].spawn=1;                           //Set spawn value of car to 1
                        C[i].path=spawn_path;                   //Set lane of the car
                        C[i].colour=randnum.nextInt(10);        //Set color of the car according to random number
                        C[i].speed=2+randnum.nextInt(5);        //Select speed of car according to random number
                        C[i].set();                             //Call car class's method to set values of variables of car object
                        if(C[i].path<4)                         //To mark the position taken by fresh spawn car as occupied
                        {
                        for(int k=0;k<10;k++)
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+k][(C[i].curr_y+100)/10+j]=1;//If p[i][j]=1, this means the block determined by p[i][j] is occupied
                    }
                        }
                        else
                        {
                          for(int k=0;k<10;k++)
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+j][(C[i].curr_y+100)/10+k]=1;
                    }  
                        }
                        
                        
                        }
                        
                        break;
                        }
                }   
                    
                }
               
             for(int i=0;i<50;i++) //This function determines the motion of car on the map
             {C[i].moving=1;
             
             
            
             if(C[i].spawn==1 && ((C[i].path==0)||C[i].path==1)) //Road going East to West
             
             
                {   if(traffic_light!=0)        //If value of traffic light is not 0, this means the cars need to stop on these lanes
                    {
                        if(C[i].curr_x+100>890 && C[i].curr_x+100<900)// To stop the car at the intersection
                            C[i].moving=0;
                    }
                    else
                        C[i].moving=1; 
                    
                    
                    for(int k=1;k<3;k++)        //Checking if there is any car in front
                {
                    if(p[(C[i].curr_x+200)/10+k][(C[i].curr_y+100)/10]!=0 )
                    {   C[i].moving=0; //When moving is zero, car is stopped
                        break;
                    }
                }
                 
                    
                    
                
                if(C[i].moving==1)
                {
                    if(C[i].turn==1 && C[i].curr_x>860) //For turning the car left
                {    int allow=0;
                outer:for(int k=0;k<7;k++) // This loop finds if there is any car or possible collision while turning
                {
                    for(int j=1;j<9;j++)
                    {
                        if(p[98+k][(C[i].curr_y+100)/10-j]==1)
                        {
                            allow=0;
                            break outer;
                        }
                        else
                            allow=1;
                            
                    }
                }
                     if(allow==1) //If allow=1, this means the way is clear and car can turn
                     {
                        for(int k=0;k<10;k++) //Updating the grid with car's new position
                    {   for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+k][(C[i].curr_y+100)/10+j]=0;
                     
                    }
                         C[i].path=5; //New lane
                         C[i].h=0;
                         C[i].v=1;      
                         C[i].curr_x=920;       //New fixed x co-ordinate
                         C[i].curr_y=C[i].curr_y-60; //Changing y coordinate
                         C[i].turn=0;
                        for(int k=0;k<10;k++)  //Updating the newly occupied position
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+j][(C[i].curr_y+100)/10+k]=1;
                    }
                         
                         continue; //Skips the rest of the code as the lane has changed
                         
                     }
                     else
                         continue;//If allow not equal to 1, car waits until the way is clear
                        
                    }
                    

                 for(int k=0;k<10;k++) //Updating the grid with car's new position
                    {   for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+k][(C[i].curr_y+100)/10+j]=0;
                     
                    }
                 C[i].curr_x+=C[i].speed; //Changing the postion of car
                 
                 for(int k=0;k<10;k++) //Updating the occupied space
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+k][(C[i].curr_y+100)/10+j]=1;
                    }
                }
                 
                 if(C[i].curr_x>1920) //Despawning the car after it has gone out of map
                 {
                     C[i].spawn=0;
                     --car_no;
                     for(int k=0;k<10;k++) 
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+k][(C[i].curr_y+100)/10+j]=0;
                    }
                 }
                 
                }
             
             if(C[i].spawn==1 && (C[i].path==2 || C[i].path==3)) //Road going West to East
             {if(C[i].curr_x<=-60) //Despawning the car after it has gone out of map
                 {
                     C[i].spawn=0;
                     --car_no;
                     for(int k=0;k<10;k++) 
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+k][(C[i].curr_y+100)/10+j]=0;
                    }
                     continue;
                 }
             if(traffic_light!=1)   //If traffic light is not one, the traffic with source west is not allowed
                    {
                        if(C[i].curr_x<1060 && C[i].curr_x>1050)
                            C[i].moving=0;
                    }
                    else
                        C[i].moving=1;
                 
             for(int k=2;k<5;k++)
             if(p[(C[i].curr_x+100)/10-k][(C[i].curr_y+100)/10]!=0 )//Checking if there is any car in front
                
                {   C[i].moving=0; //When moving is zero, car is stopped
                    break;
                }
                 
                 if(C[i].moving==1)
                 {
                 if(C[i].turn==2 && C[i].curr_x<930) //For turning the car
                {    int allow=0;
                outer:for(int k=2;k<10;k++)   //Same function as above, only the relative coordinates and math has changed
                {
                    for(int j=1;j<9;j++)
                    {
                        if(p[100+k][(C[i].curr_y+100)/10-j]==1)
                        {
                            allow=0;
                            break outer;
                        }
                        else
                            allow=1;
                            
                    }
                }
                     if(allow==1)  //Car can turn
                     {
                        for(int k=0;k<10;k++) //Updating the grid with car's new position
                    {   for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+k][(C[i].curr_y+100)/10+j]=0;
                     
                    }
                         C[i].path=5;
                         C[i].h=0;
                         C[i].v=1;
                         C[i].curr_x=920;
                         C[i].curr_y=C[i].curr_y-60;
                         C[i].turn=0;
                        for(int k=0;k<10;k++)
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+j][(C[i].curr_y+100)/10+k]=1;
                    }
                         
                         continue;
                         
                     }
                     else
                         continue;
                        
                    }
                 for(int k=0;k<10;k++) //Updating the grid with car's new position
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+k][(C[i].curr_y+100)/10+j]=0;
                    }
                 C[i].curr_x-=C[i].speed; //Changing position of car
                 
                 for(int k=0;k<10;k++)
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+k][(C[i].curr_y+100)/10+j]=1;
                    }
              
                
                
             }
             }
             if(C[i].spawn==1 && C[i].path==4) //Car going from North to Intersection
             {      if(traffic_light!=2) //Same thing as above
                    {
                        if(C[i].curr_y+100>490 && C[i].curr_y+100<500)
                            C[i].moving=0;
                    }
                    else
                        C[i].moving=1;
                  for(int k=1;k<3;k++)      //Checking the traffic ahead
                {
                    if(p[(C[i].curr_x+100)/10][(C[i].curr_y+200)/10+k]!=0 )//Checking if there is any car in front
                    {   C[i].moving=0; //When moving is zero, car is stopped
                        break;
                    }
                }
                    
                    
                
                if(C[i].moving==1)
                {
                 if(C[i].turn==1 && C[i].curr_y>450) //For turning the car
                {    int allow=1;
                    for(int k=1;k<11;k++) //Checking if way is clear
                    {
                        if(p[(C[i].curr_x+140)/10+k][62]==1)
                        { allow=0;
                            break;
                        }
                    }
                     if(allow==1)
                     {
                        for(int k=0;k<10;k++) //Updating the grid with car's new position
                    {   for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+j][(C[i].curr_y+100)/10+k]=0;
                     
                    }
                         C[i].path=0;
                         C[i].h=1;
                         C[i].v=0;
                         C[i].curr_x=C[i].curr_x;
                         C[i].curr_y=520;
                         C[i].turn=0;
                        for(int k=0;k<10;k++)
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+k][(C[i].curr_y+100)/10+j]=1;
                    }
                         
                         continue;
                         
                     }
                     else
                         continue;
                        
                    }
                 if(C[i].turn==2 && C[i].curr_y>600) //For turning the car right
                {    int allow=1;
                    for(int k=2;k<10;k++)
                    {
                        if(p[(C[i].curr_x+100)/10-k][76]==1)
                        { allow=0;
                            break;
                        }
                    }
                     if(allow==1)
                     {
                        for(int k=0;k<10;k++) //Updating the grid with car's new position
                    {   for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+j][(C[i].curr_y+100)/10+k]=0;
                     
                    }
                         C[i].path=2;
                         C[i].h=1;
                         C[i].v=0;
                         C[i].curr_x=C[i].curr_x-60;
                         C[i].curr_y=660;
                         C[i].turn=0;
                        for(int k=0;k<10;k++)
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+k][(C[i].curr_y+100)/10+j]=1;
                    }
                         
                         continue;
                         
                     }
                     else
                         continue;
                        
                    }
                 for(int k=0;k<10;k++) //Updating the grid with car's new position
                    {   for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+j][(C[i].curr_y+100)/10+k]=0;
                     
                    }
                 C[i].curr_y+=C[i].speed;
                 
                 for(int k=0;k<10;k++)
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+j][(C[i].curr_y+100)/10+k]=1;
                    }
                }
                 
                 
             }
             if(C[i].spawn==1 && C[i].path==5)// Cars going from intersection to North
             {
                 if(C[i].curr_y<=-60) //Despawning the car after it has gone out of map
                 {
                     C[i].spawn=0;
                     --car_no;
                     for(int k=0;k<10;k++) 
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+j][(C[i].curr_y+100)/10+k]=0;
                    }
                     continue;
                 }
                 for(int k=2;k<5;k++)
             if(p[(C[i].curr_x+100)/10][(C[i].curr_y+100)/10-k]!=0 )//Checking if there is any car in front
                
                {   C[i].moving=0; //When moving is zero, car is stopped
                    break;
                }

                 
                 if(C[i].moving==1)
                 {
                 for(int k=0;k<10;k++) //Updating the grid with car's new position
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+j][(C[i].curr_y+100)/10+k]=0;
                    }
                 C[i].curr_y-=C[i].speed;
                 
                 for(int k=0;k<10;k++)
                    {
                        for(int j=0;j<4;j++)
                            p[(C[i].curr_x+100)/10+j][(C[i].curr_y+100)/10+k]=1;
                    }
              
                
                
             }
             }
             }
            j.repaint();    //Paints the roads and cars on the map
            ++spawn_interval; 
            ++traffic_interval;
            cno.setText("Total Cars:"+car_no);
            spawn_sec=((double)(spawn_mul*5))/1000;
            
            ts.setText(Integer.toString((traff_mul*5)/1000));
            cs.setText(Integer.toString(cars_allow));
            ss.setText(Double.toString(spawn_sec));
            Thread.sleep(5);   //Thread sleeps for 5 milliseconds
            
            }
        
    
    }
        catch(Exception e)
{

}
    }


   
    /*
    Information about road dimensions for future use
    Horizontal road is drawn between x=0 to x=1920 and y=500 to y=800
    Vertical road is drawn between x=900 to x=1050 and y=0 to y=500
    Length of car=100 pixels
    Width of car=42 pixels
    Width of one lane=75 pixels
    */
    class MyPanel extends JPanel //This class contains the paint method
{ 
   
   
        @Override
  public void paintComponent(Graphics g)
  {
      Graphics2D g2=(Graphics2D)g;
      Rectangle2D hroad = new Rectangle2D.Float();//For creating horizontal road
      Polygon EW=new Polygon(new int[]{700,800,800,850,800,800,765,765,775,750,725,735,735,700},new int[]{440,440,450,425,400,410,410,390,390,340,390,390,410,410},14); //For painting traffic signals
      Polygon WE=new Polygon(new int[]{1150,1150,1250,1250,1215,1215,1225,1200,1175,1185,1185,1150,1150,1100},new int[]{950,940,940,910,910,890,890,840,890,890,910,910,900,925},14);
      Polygon N=new Polygon(new int[]{1150,1150,1250,1250,1300,1250,1250,1220,1220,1180,1180,1150,1150,1100},new int[]{450,440,440,450,425,400,410,410,350,350,410,410,400,425},14);
      Rectangle2D back=new Rectangle2D.Float(); 
      Rectangle2D vroad=new Rectangle2D.Float(); //Vertical road
      Rectangle2D car=new Rectangle2D.Float();//Painting car
      hroad.setFrame(0,500,1920,300);       //Setting dimensions of shapes
      vroad.setFrame(900,0,150,500);
      back.setFrame(0,330,1920,700);
      g2.draw(hroad); //Drawing Horizontal Road
      g2.draw(vroad); //Drawing Vertical Road
      g2.setColor(Color.LIGHT_GRAY);
      g2.draw(back);
      g2.fill(back);
      g2.setColor(Color.black);
      g2.fill(hroad);
      g2.fill(vroad);
      g2.setColor(Color.white);  
      g2.draw(new Line2D.Double(0,650,900,650)); //Drawing Lines that divides the road into lanes
      g2.draw(new Line2D.Double(1050,650,1920,650));
      g2.draw(new Line2D.Double(975,0,975,500));
      for(int i=0;i<900;i=i+200)
      {
          g2.draw(new Line2D.Double(i,575,i+100,575));
          g2.draw(new Line2D.Double(i,725,i+100,725));
      }
      for(int i=1050;i<1920;i=i+200)
      {
          g2.draw(new Line2D.Double(i,575,i+100,575));
          g2.draw(new Line2D.Double(i,725,i+100,725));
      }
      
      if(traffic_light==0) //Drawing traffic signals according to the traffic light
      {   Ellipse2D.Double circle1=new Ellipse2D.Double(1200,350,100,100);
          Ellipse2D.Double circle2=new Ellipse2D.Double(1200,850,100,100);  
          g2.setColor(Color.BLACK);
          g2.draw(EW);
          g2.draw(circle1);
          g2.draw(circle2);
          g2.setColor(Color.GREEN);
          
          g2.fill(EW);
          g2.setColor(Color.red);
          
          
          g2.fill(circle1);
          
          
          g2.fill(circle2);
          
      }
      else if(traffic_light==1)
      {   Ellipse2D.Double circle1=new Ellipse2D.Double(750,350,100,100);  
          Ellipse2D.Double circle2=new Ellipse2D.Double(1200,350,100,100);
          g2.setColor(Color.black);
          g2.draw(WE);
           g2.draw(circle1);
          g2.draw(circle2);
          g2.setColor(Color.GREEN);
          g2.fill(WE);
          
          
          
         
          g2.setColor(Color.red);
          g2.fill(circle1);
          g2.fill(circle2);
          
          
         
      }
      else
      {   Ellipse2D.Double circle1=new Ellipse2D.Double(750,350,100,100);
           Ellipse2D.Double circle2=new Ellipse2D.Double(1200,850,100,100);  
          g2.setColor(Color.black);
          g2.draw(circle1);
          g2.draw(circle2);
          g2.draw(N);
          g2.setColor(Color.GREEN);
          g2.fill(N);
          g2.setColor(Color.red);
          
          
          g2.fill(circle1);
         
          
          g2.fill(circle2);
      }

      
      for(int i=0;i<50;i++) //For choosing color of the car and paiting the car on the map
      {
          if(C[i].spawn==0)
              continue;
          
          if(C[i].h==1)
            car.setFrame(C[i].curr_x,C[i].curr_y,100,40);
          if(C[i].v==1)
            car.setFrame(C[i].curr_x,C[i].curr_y,40,100);
          if(C[i].colour==0)
              g2.setColor(Color.red);
          if(C[i].colour==1)
              g2.setColor(Color.white);
          if(C[i].colour==2)
              g2.setColor(Color.blue);
          if(C[i].colour==3)
              g2.setColor(Color.green);
          if(C[i].colour==4)
              g2.setColor(Color.MAGENTA);
          if(C[i].colour==5)
              g2.setColor(Color.gray);
          if(C[i].colour==6)
              g2.setColor(Color.cyan);
          if(C[i].colour==7)
              g2.setColor(Color.yellow);
          if(C[i].colour==8)
              g2.setColor(Color.LIGHT_GRAY);
          if(C[i].colour==9)
              g2.setColor(Color.pink);
          
          g2.draw(car);
          g2.fill(car);
          
          
      }
      
  }
          
}
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        getContentPane().setLayout(null);

        jButton1.setFont(new java.awt.Font("Ravie", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(170, 0, 0));
        jButton1.setText("Start Simulation");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(760, 620, 330, 70);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Permanent Marker", 1, 110)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 160, 122));
        jLabel1.setText("Traffic Simulator");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(310, 130, 1110, 120);

        jLabel3.setFont(new java.awt.Font("Permanent Marker", 0, 90)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 0));
        jLabel3.setText("The Uncool Version");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(520, 330, 940, 100);
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 1920, 1080);
        jLabel2.setIcon(iconLogo);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
this.setVisible(false); //Starting the simulation
Driver D=new Driver();
D.j.setVisible(true);
D.start();// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewJFrame F=new NewJFrame();
                F.setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
