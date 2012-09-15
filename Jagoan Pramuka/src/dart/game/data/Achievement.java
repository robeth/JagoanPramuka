/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.data;

/**
 *
 * @author Maviosso
 */
public class Achievement {
    private String name, description, imagePath;
    private boolean unlocked;
    private AchievementCondition condition;

    public Achievement(String name, String description, String imagePath, boolean unlocked, AchievementCondition condition) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.unlocked = unlocked;
        this.condition = condition;
    }

    public boolean checkUnlockCondition(){
        if(condition != null)
            return condition.isSatisfyCondition();
        return false;
    }
    
    public AchievementCondition getCondition() {
        return condition;
    }

    public void setCondition(AchievementCondition condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
    
    public static abstract class AchievementCondition{
        public abstract boolean isSatisfyCondition();
        
    }
}


