import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CityChoose implements ActionListener {

    private JFrame frame = new JFrame("city list");

    private JButton createCity = new JButton("create city");


    private JButton openCity = new JButton("open city");
    private Container south = new Container();
    private Container center = new Container();

    public static void main(String[] args) {
        new CityChoose();
    }

    public CityChoose() {
        frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());
        center.setLayout(new FlowLayout());
        frame.add(center, BorderLayout.CENTER);
        initCenter();
        south.setLayout(new GridLayout(1, 2));
        south.add(createCity);
        createCity.addActionListener(this);
        south.add(openCity);
        openCity.addActionListener(this);
        frame.add(south, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == createCity) {
            String name = JOptionPane.showInputDialog("Please input city name:");
            if (name == null || name.equals("")) {
                return;
            }
            City city = new City(name);
            //road default in city
            Road roadStart = new Road();
            city.addRoad(roadStart);
            Const.cities.add(city);
            updateCenter();
        } else if (source == openCity) {
            if (Const.cities.size() == 0) {
                JOptionPane.showMessageDialog(null, "please create city first.");
                return;
            }

            String[] options = new String[Const.cities.size()];
            for (int i = 0; i < Const.cities.size(); i++) {
                options[i] = Const.cities.get(i).getName();
            }
            String name = (String) JOptionPane.showInputDialog(null, "Please select a city:",
                    "Tip", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (name == null) {
                return;
            }
            Const.cityName = name;
            frame.dispose();
            new Simulator();
        } else {
            Const.cityName = ((JButton) source).getText();
            frame.dispose();
            new Simulator();
        }
    }

    private void initCenter() {
        //at first time ,will init city and road info
        Const.savedCity.clear();
        Const.cities.clear();
        Const.savedCity.addAll(SaveFile.readAllCity("city.txt"));
        for (int i = 0; i < Const.savedCity.size(); i++) {
            String name = Const.savedCity.get(i);
            City cityInfo=SaveFile.readCityInfo(name);
            Const.cities.add(cityInfo);
        }
        updateCenter();
    }

    private void updateCenter() {
        center.removeAll();
        for (int i = 0; i < Const.cities.size(); i++) {
            JButton jButton = new JButton(Const.cities.get(i).getName());
            jButton.addActionListener(this);
            center.add(jButton);
        }
        center.revalidate();
    }
}
