/*
 * Copyright (c) 2022, Thomas Meaney
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.eintosti.buildsystem.api.event.data;

import com.eintosti.buildsystem.api.world.BuildWorld;

/**
 * @author einTosti
 */
public class BuildWorldProjectChangeEvent extends BuildWorldDataChangeEvent<String> {

    public BuildWorldProjectChangeEvent(BuildWorld buildWorld, String oldProject, String newProject) {
        super(buildWorld, oldProject, newProject);
    }

    public String getOldProject() {
        return getOldData();
    }

    public String getNewProject() {
        return getNewData();
    }
}