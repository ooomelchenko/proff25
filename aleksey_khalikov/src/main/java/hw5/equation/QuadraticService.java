package hw5.equation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class QuadraticService {
    private List<Solution> solutionList;

    public void setSolutionList(List<Solution> solutionList) {
        this.solutionList = solutionList;
    }

    public QuadraticService() {
        solutionList = new ArrayList<>();
    }

    public List<Solution> getSolutionList() {
        return solutionList;
    }

    public void solve(int fromA, int toA, int fromB, int toB, int fromC, int toC) throws SQLException {
        SolutionJDBCManager solutionJDBCManager = new SolutionJDBCManager();
        Solution solution;
        a:for (int i=fromA;i<toA;i++){
            for (int j=fromB;j<toB;j++) {
                for (int k = fromC; k < toC; k++) {
                    if (solutionList.size()>10000) {
                        break a;
                    }
                    solution = solving(i,j,k);
                    if (solution!= null) {
                        solutionList.add(solution);
                        solutionJDBCManager.create(solution);
                        solution.print();
                    }
                }
            }
        }
    }

    public Solution solving(int koefA, int koefB, int koefC){
        double res1;
        double res2;

        if (koefA == 0){
            if (koefB == 0) {
                if (koefC == 0) {
                    return new Solution(koefA, koefB, koefC, 1, 1);
                } else {
                    return null;
                }
            } else {
                res1 = res2 = -koefC / koefB;
                return new Solution(koefA,koefB,koefC,res1,res2);
            }
        }


        double disk = Math.pow(koefB,2)-4*koefA*koefC;
        if (disk>=0) {
            res1 = (-koefB + Math.sqrt(disk)) / 2 / koefA;
            res2 = (-koefB - Math.sqrt(disk)) / 2 / koefA;
            return new Solution(koefA,koefB,koefC,res1,res2);
        } else {
            return null;
        }
    }
}
